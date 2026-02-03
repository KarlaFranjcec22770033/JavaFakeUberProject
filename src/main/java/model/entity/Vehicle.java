package model.entity;

import java.io.Serializable;
import java.util.Objects;

/**
 * stvara novo vozilo za vozaca
 */

public class Vehicle implements Comparable<Vehicle>,Serializable {

    private static int nextId=1;

    private static int id;
    private String model;
    private String color;
    private String registration;
    private Integer driverId;

    @Override
    public int compareTo(Vehicle o) {
        return this.getModel().compareTo(o.getModel());
    }

    public Vehicle(){
        this.id=nextId++;
    }

    public Vehicle(String model, String color, String registration) {
        this.id=nextId++;
        this.model = model;
        this.color = color;
        this.registration = registration;
    }

    public static void setId(int id) {
        Vehicle.id = id;
    }

    public Integer getDriverId() {
        return driverId;
    }

    public void setDriverId(Integer driverId) {
        this.driverId = driverId;
    }
    public String getModel() {return model;}
    public String getAll(){return this.toString();}
    public int getId() {return id;}

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Vehicle vehicle = (Vehicle) o;
        return id == vehicle.id && Objects.equals(registration, vehicle.registration);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, registration);
    }

    @Override
    public String toString() {
        return this.model+"-"+this.registration+"-"+this.driverId+"-"+this.color;
    }
    public void setModel(String model) {
        this.model = model;
    }
    public String getRegistration() {
        return registration;
    }
    public void setRegistration(String registration) {
        this.registration = registration;
    }
    public String getColor() {
        return color;
    }
    public void setColor(String color) {
        this.color = color;
    }

}
