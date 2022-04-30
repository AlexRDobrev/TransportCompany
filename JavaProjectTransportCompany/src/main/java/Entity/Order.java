package Entity;


import javax.persistence.*;
import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

@Entity
@Table(name="orders")
public class Order implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long id;

    @Column(name="startingPoint", nullable = false)
    private String startingPoint;

    @Column(name="finalDestination", nullable = false)
    private String finalDestination;

    @Column(name="dateOfTravel", nullable = false)
    private LocalDate dateOfTravel;

    @Column(name="dateOfArrival", nullable = false)
    private LocalDate dateOfArrival;

    @Column(name="IsPaid", nullable = false)
    private boolean IsPaid;

    @Column(name="price", nullable = false)
    private double price;

    @Transient
    private double weight;

    @Transient
    boolean addedtoincome=false;

    @Transient
    int peoplecount;

    @ManyToOne
    private Driver driver;

    @ManyToOne
    private Client client;


    public Order() {
        this.IsPaid=false;
    }

    public Order(long id, String startingPoint, String finalDestination,LocalDate dateOfTravel,LocalDate dateOfArrival,double weight) {
        this.id = id;
        this.startingPoint=startingPoint;
        this.finalDestination=finalDestination;
        this.dateOfTravel=dateOfTravel;
        this.dateOfArrival=dateOfArrival;
        this.weight=weight;
        this.IsPaid=false;
        this.price=weight*7.50;
    }
    public Order(long id, String startingPoint, String finalDestination,LocalDate dateOfTravel,LocalDate dateOfArrival,int peoplecount) {
        this.id = id;
        this.startingPoint=startingPoint;
        this.finalDestination=finalDestination;
        this.dateOfTravel=dateOfTravel;
        this.dateOfArrival=dateOfArrival;
        this.peoplecount=peoplecount;
        this.IsPaid=false;
        this.price=peoplecount*10.50;
    }

    public void Save_Order_Data(String FileOutput){
        try(FileOutputStream fos=new FileOutputStream(FileOutput);
            ObjectOutputStream outputStream=new ObjectOutputStream(fos)
        ){
            outputStream.writeObject(this);
        }catch(IOException ex){
            System.err.println(ex);
        }
    }
    public void Read_Order_Data(String FileInput){

        try (FileInputStream fis = new FileInputStream(FileInput);
             ObjectInputStream inputStream = new ObjectInputStream(fis)) {
            Scanner scanner=new Scanner(new File(FileInput));
            List<Order> orderList=new ArrayList<>();
            while(scanner.hasNext()){
                Order order =(Order) inputStream.readObject();
            }
        }catch(ClassNotFoundException ex){
            System.err.println("Class not found: "+ex);
        }catch(IOException ex){
            System.out.println("IO error:"+ex);
        }
    }

    public long getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStartingPoint() {
        return startingPoint;
    }

    public void setStartingPoint(String startingPoint) {
        this.startingPoint = startingPoint;
    }

    public String getFinalDestination() {
        return finalDestination;
    }
    public void setFinalDestination(String finalDestination) {
        this.finalDestination = finalDestination;
    }

    public double getWeight() {
        return weight;
    }

    public LocalDate getDateOfTravel() {
        return dateOfTravel;
    }

    public void setDateOfTravel(LocalDate dateOfTravel) {
        this.dateOfTravel = dateOfTravel;
    }

    public LocalDate getDateOfArrival() {
        return dateOfArrival;
    }

    public void setDateOfArrival(LocalDate dateOfArrival) {
        this.dateOfArrival = dateOfArrival;
    }

    public boolean isPaid() {
        return IsPaid;
    }

    public void setPaid(boolean paid) {
        IsPaid = paid;
    }

    public double getPrice() {
        return price;
    }

    public boolean isAddedtoincome() {
       return addedtoincome;
    }

    public void setAddedtoincome(boolean completed) {
        this.addedtoincome = completed;
    }

    public static Comparator<Order> CompareByFinalDestination=new Comparator<Order>() {
        @Override
        public int compare(Order o1, Order o2) {
            return o1.finalDestination.compareTo(o2.finalDestination);
        }
    };

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", startingpoint=" + startingPoint + ", finaldestination=" + finalDestination + ", dateoftravel=" + dateOfTravel +
                ", dateofarrival=" + dateOfArrival +", weight=" + weight +", IsPaid=" + IsPaid+'\'' +
                '}';
    }
}
