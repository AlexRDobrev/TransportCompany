package Entity;

import Dao.DriverDao;
import Dao.OrderDao;
import Qualification.DriverQualification;
import com.mysql.cj.x.protobuf.MysqlxCrud;

import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import javax.persistence.*;

@Entity
@Table(name="drivers")

public class Driver {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name="name", nullable = false)
    private String name;

    @Column(name="qualification", nullable = false)
    private DriverQualification qualification;

    @Column(name="salary", nullable = false)
    private double salary;

    @ManyToOne(fetch = FetchType.LAZY)
    private static TransportCompany transportCompany;

    @OneToMany
    @JoinColumn(name = "driver_id")
    private List<Order> listoforders;

    public Driver() {
    }
    public Driver(int id,String name) {
        this.id=id;
        this.name = name;
    }

    public Driver(String name) {
        this.name = name;
        this.qualification=DriverQualification.None;
        this.listoforders=new ArrayList<>();
    }

    public Driver(int id, String name,DriverQualification qualification,double salary) {
        this.id = id;
        this.name = name;
        this.qualification=qualification;
        this.salary=salary;
        this.listoforders=new ArrayList<>();
    }

    public void takeOrder(Order order){
            listoforders.add(order);
            order.setAddedtoincome(false);
        DriverDao.updateEmployee(this);
    }
    public void declineOrder(Order order){
        listoforders.remove(order);
    }

    public void cleanCompletedOrders() {
        listoforders.removeIf(order -> order.isPaid());
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

    public List<Order> getListoforders() {
        return listoforders;
    }

    public static Comparator<Driver> CompareByQualification=new Comparator<Driver>() {
        @Override
        public int compare(Driver o1,Driver o2) {
            return o1.qualification.compareTo(o2.qualification);
        }
    };

    public static Comparator<Driver> CompareBySalary=new Comparator<Driver>() {
        @Override
        public int compare(Driver o1,Driver o2) {
            return Double.compare(o1.salary,o2.salary);
        }
    };


    @Override
    public String toString() {
        return "Drivers{" +
                "id=" + id +
                ", name='" + name +'\'' +
                '}';
    }
}
