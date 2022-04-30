package Entity;

import Dao.DriverDao;
import Dao.TransportCompanyDao;
import Dao.VehicleDao;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="transportCompanies")

public class TransportCompany {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name="name", nullable = false)
    private String name;

    @Column(name = "income",nullable = false)
    private double income;


    @Transient
    private List<Double> incomes_time;

   @OneToMany
   @JoinColumn(name = "transportCompany_id")
    private List<Driver> driversList;

    @OneToMany
    @JoinColumn(name="transportCompany_id")
    private List<Vehicle> vehiclesList;

//    @OneToMany()
//    @JoinColumn(name = "transportCompany_id")
//    private List<Order> transportOrdersList;

    public TransportCompany() {}

    public TransportCompany(String name) {
        this.name = name;
    }

    public TransportCompany(int id, String name) {
        this.name = name;
        this.driversList=new ArrayList<>();
        this.vehiclesList=new ArrayList<>();
        this.income=0;
        incomes_time=new ArrayList<>();

    }
    public TransportCompany(int id, String name,List<Driver> driversList) {
        this.id = id;
        this.name = name;
        this.driversList=driversList;
        this.vehiclesList=new ArrayList<>();
        this.income=0;
        incomes_time=new ArrayList<>();
    }

    public TransportCompany(int id, String name, List<Vehicle> vehiclesList, List<Driver> driversList) {
        this.id = id;
        this.name = name;
        this.vehiclesList=vehiclesList;
        this.driversList=driversList;
        this.income=0;
        incomes_time=new ArrayList<>();

    }
    public void AddNewDriver(Driver driver){
        if(!driversList.contains(driver)){
            driversList.add(driver);
            DriverDao.saveEmployee(driver);
        }

    }

    public void fireEmployee(Driver driver){
            this.driversList.remove(driver);
   }

    public void addNewVehicle(Vehicle vehicle){
            this.vehiclesList.add(vehicle);
        VehicleDao.saveVehicle(vehicle);
    }
    public void removeVehicle(Vehicle vehicle){
        this.vehiclesList.remove(vehicle);
    }

    public void getCompletedOrdersFromDrivers(){
        this.getDriversList().forEach(driver -> System.out.println("Driver " +driver.getName()+" has completed "+driver.getListoforders().size() +" orders!"));
    }
    public double CalculateIncome(){
        double helpinghand=0;
        double secondhelpinghand=0;
        for(Driver driver:driversList){
            for(Order order:driver.getListoforders()){
                if(order.isPaid() & !order.isAddedtoincome()){
                    income+=order.getPrice();
                    helpinghand+=order.getPrice();
                    secondhelpinghand+=order.getPrice();
                    order.setAddedtoincome(true);
                }
            }
            System.out.println(driver.getName()+" has accumulated "+secondhelpinghand+" for "+this.name);
            secondhelpinghand=0;
        }
        incomes_time.add(helpinghand);
        TransportCompanyDao.updateCompany(this);
        return income;
    }

    public void Display_Incomes_By_Month(){
        for(double income:incomes_time){
            System.out.println("For the month the company has accumulated: "+income+" lv");
            }
        }

    public void FilterDriversByQualification(){
        driversList.stream().sorted(Driver.CompareByQualification).forEach(System.out::println);
    }

    public void FilterDriversBySalary(){
        driversList.stream().sorted(Driver.CompareBySalary).forEach(System.out::println);
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

    public List<Driver> getDriversList() {
        return driversList;
    }

    public List<Vehicle> getVehiclesList() {
        return vehiclesList;
    }

    public void setVehiclesList(List<Vehicle> vehiclesList) {
        this.vehiclesList = vehiclesList;
    }

    public double getIncome() {
        return income;
    }

    @Override
    public String toString() {
        return "TransportCompany{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

}
