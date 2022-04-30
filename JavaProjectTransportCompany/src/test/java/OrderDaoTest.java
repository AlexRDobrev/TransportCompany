import Dao.DriverDao;
import Dao.OrderDao;
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

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class OrderDaoTest {



        private static SessionFactory sessionFactory;
        private Session session;
        private Order order;
        private static Order orderStatic;

        @BeforeAll
        public static void setup() {
            orderStatic = new Order(1,"Bulgaria","Germany",
                    LocalDate.of(2021,12,2),LocalDate.of(2021,12,21),25.5);
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
            Long id = (Long) session.save(order);
            session.getTransaction().commit();

            Assertions.assertTrue(id > 0);
        }

        @Test
        public void testUpdate() {
            session.beginTransaction();
            session.save(order);
            order.setFinalDestination("Russia");
            session.update(order);
            session.getTransaction().commit();

            Assertions.assertTrue(session.get(Order.class,order.getId()).getFinalDestination().equals("Russia"));
        }

        @Test
        public void testDelete() {
            session.beginTransaction();
            session.save(order);
            session.delete(order);
            session.getTransaction().commit();

            Assertions.assertFalse(session.contains(order));
        }

        @Test
        public void testList() {
            List<Order> orders= new ArrayList<>();
            orders.add(order);
            OrderDao.saveOrders(orders);
            List<Order> ordersAfterSave=OrderDao.readOrders();
            Assertions.assertTrue(ordersAfterSave.size()<3);
        }



        @BeforeEach
        public void openSession() {
            order=orderStatic;
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
