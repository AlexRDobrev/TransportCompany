package Dao;

import Configuration.SessionFactoryUtil;
import Entity.Client;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class ClientDao {
    public static void saveClient(Client client) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.save(client);
            transaction.commit();
        }
    }

    public static void updateClient(Client client) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.update(client);
            transaction.commit();
        }
    }

    public static void saveClients(List<Client> clientList) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            clientList.stream().forEach((com) -> session.save(com));
            transaction.commit();
        }
    }

    public static List<Client> readClients() {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            return session.createQuery("SELECT a FROM Client a", Client.class).getResultList();
        }
    }
    public static List<String> readClientNames(){
        try(Session session=SessionFactoryUtil.getSessionFactory().openSession()){
            return session.createQuery("SELECT name FROM Client",String.class).getResultList();
        }


    }
}
