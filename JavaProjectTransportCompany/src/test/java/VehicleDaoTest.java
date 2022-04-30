import Dao.DriverDao;
import Dao.VehicleDao;
import Entity.*;
import Entity.Order;
import Qualification.TypeOfVehicle;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.List;


public class VehicleDaoTest {


        private static SessionFactory sessionFactory;
        private Session session;
        private Vehicle vehicle;
        private static Vehicle vehicleStatic;

        @BeforeAll
        public static void setup() {
            vehicleStatic = new Vehicle(TypeOfVehicle.Bus);
            if (sessionFactory == null) {
                Configuration configuration = new Configuration();
                configuration.addAnnotatedClass(TransportCompany.class);
                configuration.addAnnotatedClass(Vehicle.class);
                configuration.addAnnotatedClass(Driver.class);
                configuration.addAnnotatedClass(Order.class);
                configuration.addAnnotatedClass(Client.class);
                ServiceRegistry serviceRegistry
                        = new StandardServiceRegistryBuilder()
                        .applySettings(configuration.getProperties()).build();

                sessionFactory = configuration.buildSessionFactory(serviceRegistry);
            }
            System.out.println("SessionFactory created");

        }

        @AfterAll
        public static void tearDown() {
            if (sessionFactory != null) sessionFactory.close();
            System.out.println("SessionFactory destroyed");
        }

        @Test
        public void testCreateVehicle() {
            session.beginTransaction();
            Long id = (Long) session.save(vehicle);
            session.getTransaction().commit();

            Assertions.assertTrue(id > 0);
        }

        @Test
        public void testUpdate() {
            session.beginTransaction();
            session.save(vehicle);
            vehicle.setTypeOfVehicle(TypeOfVehicle.Tanker);
            session.update(vehicle);
            session.getTransaction().commit();

            Assertions.assertTrue(session.get(Vehicle.class,vehicle.getId()).getTypeOfVehicle().equals(TypeOfVehicle.Tanker));
        }

        @Test
        public void testDelete() {
            session.beginTransaction();
            session.save(vehicle);
            session.delete(vehicle);
            session.getTransaction().commit();

            Assertions.assertFalse(session.contains(vehicle));
        }

        @Test
        public void testList() {
            List<Vehicle> vehicles= new ArrayList<>();
            vehicles.add(vehicle);
            VehicleDao.saveVehicles(vehicles);
            List<Vehicle> vehiclesAfterSave=VehicleDao.readVehicles();
            Assertions.assertTrue(vehiclesAfterSave.size()<3);
        }



        @BeforeEach
        public void openSession() {
            vehicle=vehicleStatic;
            session = sessionFactory.openSession();
            System.out.println("Session created");
        }

        @AfterEach
        public void closeSession() {
            if (session != null){
                session.close();
            }

            System.out.println("Session closed\n");
        }

    }
