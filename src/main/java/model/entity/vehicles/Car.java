package model.entity.vehicles;

import model.entity.Vehicle;

public class Car extends Vehicle implements Comparable<Vehicle> {
    String name;
    public Car(){super();}
    public Car(String model, String color, String registration) {
        super(model,color,registration);
    }

    public Car(String name){this.name=name;}
}
