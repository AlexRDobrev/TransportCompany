import Dao.DriverDao;
import Dao.OrderDao;
import Dao.TransportCompanyDao;
import Entity.Driver;
import Entity.Order;
import Entity.TransportCompany;
import Entity.Vehicle;
import Qualification.DriverQualification;
import Qualification.TypeOfVehicle;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TransportCompanyIncomeTest {

    private TransportCompany transportCompany;
    private static TransportCompany transportCompanyStatic;

    @BeforeAll
    static void initAll(){
        List<Driver> driverList=new ArrayList<>();
        List<Vehicle> vehiclesList=new ArrayList<>();
        transportCompanyStatic=new TransportCompany(1,"Speedy",vehiclesList,driverList);
    }

    @BeforeEach
    void init() {
        Driver emp1=new Driver(1,"Gosho", DriverQualification.Flammable,1200);
        Driver emp2=new Driver(2,"Mitra",DriverQualification.Flammable,1500);
        Driver emp3=new Driver(3,"Sofron",DriverQualification.Glass,850);
        DriverDao.saveEmployee(emp1);
        DriverDao.saveEmployee(emp2);
        DriverDao.saveEmployee(emp3);
        Order order1=new Entity.Order(1,"Bulgaria","Germany",
                LocalDate.of(2021,12,2),LocalDate.of(2021,12,21),25.5);
        Order order2=new Entity.Order(1,"Russia","America",
                LocalDate.of(2021,3,21),LocalDate.of(2021,4,13),45.5);
        Order order3=new Entity.Order(1,"Norway","England",
                LocalDate.of(2021,5,15),LocalDate.of(2021,6,12),105.5);
        Order order4=new Order(1,"India","China",
                LocalDate.of(2020,8,25),LocalDate.of(2021,9,3),255.5);
        OrderDao.saveOrder(order1);
        OrderDao.saveOrder(order2);
        OrderDao.saveOrder(order3);
        OrderDao.saveOrder(order4);
        Vehicle vehicle1=new Vehicle(1, TypeOfVehicle.Bus);
        Vehicle vehicle2=new Vehicle(2, TypeOfVehicle.Tanker);
        Vehicle vehicle3=new Vehicle(3, TypeOfVehicle.Truck);
        order4.setPaid(true);
        order1.setPaid(true);
        order2.setPaid(true);
        emp1.takeOrder(order1);
        emp1.takeOrder(order2);
        emp2.takeOrder(order4);
        emp3.takeOrder(order3);
        List<Driver> driverList=new ArrayList<>();
        driverList.add(emp2);
        transportCompany = new TransportCompany(1,"Speedy",driverList);
        TransportCompanyDao.saveCompany(transportCompany);
    }

    @Test
    @DisplayName("Proper calculation of company income")
    public void CalculatedIncomeTest(){
        double expected=1916.25;
        double actual=transportCompany.CalculateIncome();
        assertEquals(expected,actual);
    }

}
