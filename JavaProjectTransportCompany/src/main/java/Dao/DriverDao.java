package Dao;

import Configuration.SessionFactoryUtil;
import Entity.Driver;
import Entity.Order;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class DriverDao {
    public static void saveEmployee(Driver driver) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.save(driver);
            transaction.commit();
        }
    }

    public static void updateEmployee(Driver driver) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.update(driver);
            transaction.commit();
        }
    }

    public static void saveEmployees(List<Driver> driversList) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            driversList.stream().forEach((dr) -> session.save(dr));
            transaction.commit();
        }
    }

    public static List<Driver> readDrivers() {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            return session.createQuery("SELECT a FROM Driver a", Driver.class).getResultList();
        }
    }
    public static List<String> readDriverNames(){
        try(Session session=SessionFactoryUtil.getSessionFactory().openSession()){
            return session.createQuery("SELECT name FROM Driver",String.class).getResultList();
        }
    }


    public static List<Driver> filterDriversByQualificationAndSalary(){
        try(Session session=SessionFactoryUtil.getSessionFactory().openSession()){
           return session.createQuery("SELECT a FROM Driver a order by qualification,salary",Driver.class).getResultList();
        }
    }
}
