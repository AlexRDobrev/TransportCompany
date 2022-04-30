import Dao.TransportCompanyDao;
import Entity.*;
import Entity.Order;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.List;

public class TransportCompanyDaoTest {

        private static SessionFactory sessionFactory;
        private Session session;
        private TransportCompany transportCompany;
        private static TransportCompany transportCompanyStatic;

        @BeforeAll
        public static void setup() {
            transportCompanyStatic = new TransportCompany("Speedy");
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
        public void testCreateCompany() {
            session.beginTransaction();
            Long id = (Long) session.save(transportCompany);
            session.getTransaction().commit();

            Assertions.assertTrue(id > 0);
        }

        @Test
        public void testUpdate() {
            session.beginTransaction();
            session.save(transportCompany);
            transportCompany.setName("Econt");
           session.update(transportCompany);
           session.getTransaction().commit();

            Assertions.assertTrue(session.get(TransportCompany.class,transportCompany.getId()).getName().equals("Econt"));
        }

    @Test
    public void testDelete() {
        session.beginTransaction();
        session.save(transportCompany);
        session.delete(transportCompany);
        session.getTransaction().commit();

        Assertions.assertFalse(session.contains(transportCompany));
    }

        @Test
        public void testList() {
            List<TransportCompany> transportCompanies= new ArrayList<>();
            transportCompanies.add(transportCompany);
            TransportCompanyDao.saveCompanies(transportCompanies);
            List<TransportCompany> transportCompaniesAfterSave=TransportCompanyDao.readCompanies();
            Assertions.assertTrue(transportCompaniesAfterSave.size()<3);
        }



        @BeforeEach
        public void openSession() {
            transportCompany=transportCompanyStatic;
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
