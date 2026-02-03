package model.entity.vehicles;

import model.entity.Vehicle;

public class Van extends Vehicle implements Comparable<Vehicle> {
    String name;
    public Van(String name){this.name=name;}
    public Van(){}
    public Van(String model, String color, String registration) {
        super(model,color,registration);
    }
}
