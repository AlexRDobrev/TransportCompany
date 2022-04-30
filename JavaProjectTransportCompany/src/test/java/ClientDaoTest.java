import Dao.ClientDao;
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


public class ClientDaoTest {

        private static SessionFactory sessionFactory;
        private Session session;
        private Client client;
        private static Client clientStatic;

        @BeforeAll
        public static void setup() {
            clientStatic = new Client("Pesho");
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
            Long id = (Long) session.save(client);
            session.getTransaction().commit();

            Assertions.assertTrue(id > 0);
        }

        @Test
        public void testUpdate() {
            session.beginTransaction();
            session.save(client);
            client.setName("Georgi");
            session.update(client);
            session.getTransaction().commit();

            Assertions.assertTrue(session.get(Client.class,client.getId()).getName().equals("Georgi"));
        }

        @Test
        public void testDelete() {
            session.beginTransaction();
            session.save(client);
            session.delete(client);
            session.getTransaction().commit();

            Assertions.assertFalse(session.contains(client));
        }

        @Test
        public void testList() {
            List<Client> clientList= new ArrayList<>();
            clientList.add(client);
            ClientDao.saveClients(clientList);
            List<Client> clientListAfterSave=ClientDao.readClients();
            Assertions.assertTrue(clientListAfterSave.size()<3);
        }



        @BeforeEach
        public void openSession() {
            client=clientStatic;
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
