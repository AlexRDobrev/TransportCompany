package Entity;

import Qualification.TypeOfVehicle;

import javax.persistence.*;
@Entity
@Table(name="vehicles")
public class Vehicle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name="vehicleType", nullable = false)
    private TypeOfVehicle typeOfVehicle;

    @ManyToOne
    private TransportCompany transportCompany;

    public Vehicle() {
    }

    public Vehicle(TypeOfVehicle type) {
        this.typeOfVehicle = type;
    }

    public Vehicle(int id, TypeOfVehicle typeOfVehicle) {
        this.id = id;
        this.typeOfVehicle = typeOfVehicle;
    }


    public long getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public TypeOfVehicle getTypeOfVehicle() {
        return typeOfVehicle;
    }

    public void setTypeOfVehicle(TypeOfVehicle typeOfVehicle) {
        this.typeOfVehicle = typeOfVehicle;
    }

    @Override
    public String toString() {
        return "Vehicles{" +
                "id=" + id +
                ", type='" + typeOfVehicle + '\'' +
                '}';
    }
}
