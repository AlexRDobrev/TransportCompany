package Entity;

import Dao.ClientDao;
import Dao.OrderDao;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="clients")
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long id;

    @Column(name="name", nullable = false)
    private String name;


    @OneToMany
    @JoinColumn(name="client_id")
    private List<Order> ordersFromClient;

    public Client() {
    }

    public Client(String name) {
        this.name = name;
    }

    public Client(int id, String name) {
        this.id = id;
        this.name = name;
        this.ordersFromClient=new ArrayList<>();
    }

    public Order makeANewPaidOrder(Order order){
        order.setPaid(true);
        ordersFromClient.add(order);
        OrderDao.saveOrder(order);
        ClientDao.updateClient(this);
        return order;
    }

    public Order makeANewUnPaidOrder(Order order){
        if(order.isPaid()){
            order.setPaid(false);
        }
        ordersFromClient.add(order);
        OrderDao.saveOrder(order);
        ClientDao.updateClient(this);
        return order;
    }

    public long getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Order> getOrder() {
        return ordersFromClient;
    }


    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", name='" + name + ", orders="+ordersFromClient+'\'' +
                '}';
    }
}
