import Dao.DriverDao;
import Entity.*;
import Entity.Order;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.junit.jupiter.api.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;



public class DriverDaoTest {

        private static SessionFactory sessionFactory;
        private Session session;
        private Driver driver;
        private static Driver driverStatic;

        @BeforeAll
        public static void setup() {
            driverStatic = new Driver("Gosho");
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
        public void testCreateDriver() {
            session.beginTransaction();
            Long id = (Long) session.save(driver);
            session.getTransaction().commit();

            Assertions.assertTrue(id > 0);
        }

        @Test
        public void testUpdate() {
            session.beginTransaction();
            session.save(driver);
            driver.setName("Sasha");
            session.update(driver);
            session.getTransaction().commit();

            Assertions.assertTrue(session.get(Driver.class,driver.getId()).getName().equals("Sasha"));
        }

        @Test
        public void testDelete() {
            session.beginTransaction();
            session.save(driver);
            session.delete(driver);
            session.getTransaction().commit();

            Assertions.assertFalse(session.contains(driver));
        }

        @Test
        public void testList() {
            List<Driver> drivers= new ArrayList<>();
            drivers.add(driver);
            DriverDao.saveEmployees(drivers);
            List<Driver> driversAfterSave=DriverDao.readDrivers();
            Assertions.assertTrue(driversAfterSave.size()<3);
        }

        @BeforeEach
        public void openSession() {
            driver=driverStatic;
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
