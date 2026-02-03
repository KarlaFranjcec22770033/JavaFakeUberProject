package model.entity.JSON;

import model.entity.Booking;
import model.entity.User;
import model.entity.Vehicle;

import java.io.Serializable;
import java.util.List;

public class BackupData implements Serializable {

    public List<User> users;
    public List<Booking> bookings;
    public List<Vehicle> vehicles;

    public BackupData(List<User> users, List<Booking> bookings, List<Vehicle> vehicles) {
        this.users = users;
        this.bookings = bookings;
        this.vehicles = vehicles;
    }

    public List<User> getUsers() {return users;}
    public List<Booking> getBookings() {return bookings;}
    public List<Vehicle> getVehicles() {return vehicles;}
}
