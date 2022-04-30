import Dao.*;
import Dao.DriverDao;
import Entity.*;
import Entity.Driver;
import Qualification.DriverQualification;
import Qualification.TypeOfVehicle;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;



public class Main {

    public static void saveOrders(String filepath,List<Order> al) {
        try {
            FileOutputStream fos = new FileOutputStream(filepath, true);
            try {
                ObjectOutputStream oos = new ObjectOutputStream(fos);
                for (Order o : al) {
                    try {
                        oos.writeObject(o);
                    } catch (NotSerializableException e) {
                        System.out.println("An object was not serializable, it has not been saved.");
                        e.printStackTrace();
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    public static void readOrders(String fileinput){
        List<Order> orders = new ArrayList<Order>();

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileinput))) {

            while(true){
                Order order=(Order)ois.readObject();
                orders.add(order);
            }
        } catch (EOFException ex){

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        orders.forEach(System.out::println);
    }

    public static void main(String[] args) {
        String FilePath="backup.ser";
        Client client1=new Client(1,"Pesho");
        Client client2=new Client(1,"Ivan");
        Client client3=new Client(1,"Georgi");
        Client client4=new Client(1,"Petio");
        ClientDao.saveClient(client1);
        ClientDao.saveClient(client2);
        ClientDao.saveClient(client3);
        ClientDao.saveClient(client4);
        List<Order> orderList=new ArrayList<>();
        Order order1=new Order(1,"Bulgaria","Germany",
                LocalDate.of(2021,12,2),LocalDate.of(2021,12,21),25.5);
        Order order2=new Order(1,"Russia","America",
                LocalDate.of(2021,3,21),LocalDate.of(2021,4,13),45.5);
        Order order3=new Order(1,"Norway","England",
                LocalDate.of(2021,5,15),LocalDate.of(2021,6,12),105.5);
        Order order4=new Order(1,"India","China",
                LocalDate.of(2020,8,25),LocalDate.of(2021,9,3),255.5);
        Order order5=new Order(1,"Transilvania","Irak",
                LocalDate.of(2020,8,25),LocalDate.of(2021,11,9),10);
        orderList.add(order1);
        orderList.add(order2);
        orderList.add(order3);
        TransportCompany transportCompany1=new TransportCompany(1,"Econt");
        TransportCompany transportCompany2=new TransportCompany(2,"Speedy");
        TransportCompanyDao.saveCompany(transportCompany1);
        TransportCompanyDao.saveCompany(transportCompany2);
        Driver emp1=new Driver(1,"Gosho",DriverQualification.Flammable,1200);
        Driver emp2=new Driver(2,"Mitra",DriverQualification.Flammable,1500);
        Driver emp3=new Driver(3,"Sofron",DriverQualification.Glass,850);
        Vehicle vehicle1=new Vehicle(1, TypeOfVehicle.Bus);
        Vehicle vehicle2=new Vehicle(2, TypeOfVehicle.Tanker);
        Vehicle vehicle3=new Vehicle(3, TypeOfVehicle.Truck);
        transportCompany1.AddNewDriver(emp1);
        transportCompany1.AddNewDriver(emp3);
        transportCompany2.AddNewDriver(emp2);
        transportCompany1.addNewVehicle(vehicle3);
        transportCompany2.addNewVehicle(vehicle2);
        transportCompany2.addNewVehicle(vehicle1);
        emp1.takeOrder(client1.makeANewPaidOrder(order1));
        emp1.takeOrder(client2.makeANewPaidOrder(order2));
        emp3.takeOrder(client3.makeANewUnPaidOrder(order3));
        emp2.takeOrder(client4.makeANewPaidOrder(order4));
        transportCompany1.CalculateIncome();
        transportCompany2.CalculateIncome();
        emp3.takeOrder(client3.makeANewPaidOrder(order5));
        emp2.takeOrder(client1.makeANewPaidOrder(order3));
        emp1.takeOrder(client2.makeANewPaidOrder(order4));
        transportCompany1.CalculateIncome();
        transportCompany1.Display_Incomes_By_Month();
        System.out.println(TransportCompanyDao.FilterCompaniesByNameAndIncome());
        System.out.println(DriverDao.filterDriversByQualificationAndSalary());
        System.out.println(OrderDao.filterOrdersByFinalDestination());
        transportCompany1.getCompletedOrdersFromDrivers();
        System.out.println(TransportCompanyDao.getDriverTotalOrdersPrice(transportCompany1.getId()));
        saveOrders(FilePath,orderList);
        readOrders(FilePath);
    }
}
