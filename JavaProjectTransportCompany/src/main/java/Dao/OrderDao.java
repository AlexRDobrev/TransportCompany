package Dao;

import Configuration.SessionFactoryUtil;
import Entity.Driver;
import Entity.Order;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.math.BigDecimal;
import java.util.List;

public class OrderDao {

    public static void saveOrder(Order order) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.save(order);
            transaction.commit();
        }
    }

    public static void updateOrder(Order order) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.update(order);
            transaction.commit();
        }
    }

    public static void saveOrders(List<Order> orderList) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            orderList.stream().forEach((ord) -> session.save(ord));
            transaction.commit();
        }
    }

    public static List<Order> readOrders() {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            return session.createQuery("SELECT a FROM Order a", Order.class).getResultList();
        }
    }

    public static void readOrdersTotalPrice(){
        try(Session session=SessionFactoryUtil.getSessionFactory().openSession()){
            double sum=0;
            List<Double> orderlist=session.createQuery("SELECT price FROM Order a WHERE driver_id!=0",Double.class).getResultList();
            for(double price:orderlist){
                sum+= price;
            }
            System.out.println("Total price of completed orders: "+sum);
        }
    }
    public static List<Order> filterOrdersByFinalDestination(){
        try(Session session=SessionFactoryUtil.getSessionFactory().openSession()){
            return session.createQuery("SELECT a FROM Order a order by finalDestination",Order.class).getResultList();
        }
    }
}
