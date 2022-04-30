package Dao;

import Configuration.SessionFactoryUtil;
import Entity.Driver;
import Entity.Order;
import Entity.TransportCompany;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.Collections;
import java.util.List;

public class TransportCompanyDao {
    public static void saveCompany(TransportCompany company) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.save(company);
            transaction.commit();
        }
    }

    public static void saveCompanies(List<TransportCompany> companyList) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            companyList.stream().forEach((com) -> session.save(com));
            transaction.commit();
        }
    }
    public static void updateCompany(TransportCompany company) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.update(company);
            transaction.commit();
        }
    }
    public static void deleteCompany(TransportCompany company) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.delete(company);
            transaction.commit();
        }
    }

    public static List<TransportCompany> readCompanies() {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            return session.createQuery("SELECT a FROM TransportCompany a", TransportCompany.class).getResultList();
        }
    }

    public static List<String> readCompanyNames(){
        try(Session session=SessionFactoryUtil.getSessionFactory().openSession()){
            List<String> driverNames=session.createQuery("SELECT name FROM TransportCompany",String.class).getResultList();
            Collections.sort(driverNames);
            return driverNames;
        }
    }

    public static List<TransportCompany> FilterCompaniesByNameAndIncome(){
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            return session.createQuery("SELECT a FROM TransportCompany a order by name,income", TransportCompany.class).getResultList();
        }
    }

    public static double getDriverTotalOrdersPrice(long id) {
        TransportCompany company;
        double totalPrice=0;
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            company = session.get(TransportCompany.class, id);
            for(Driver driver: company.getDriversList()){
                totalPrice += driver.getListoforders().stream().mapToDouble(Order::getPrice).sum();
            }
            transaction.commit();
        }

        return totalPrice;
    }

//    public static void readDriversOrderCount(long id) {
//        TransportCompany company;
//        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
//            Transaction transaction = session.beginTransaction();
//            company = session.createQuery(
//                            "select c from TransportCompany c" +
//                                    " join fetch c.driversList"+
//                            "where id=id",
//                            TransportCompany.class)
//                    .setParameter("id", id)
//                    .getSingleResult();
//            transaction.commit();
//        }
//        for(Driver driver:company.getDriversList()){
//            System.out.println("Driver " +driver.getName()+" has completed "+driver.getListoforders().size() +"orders!");
//        }
//    }
}
