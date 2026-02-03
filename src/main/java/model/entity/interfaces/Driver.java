package model.entity.interfaces;

import model.entity.ENUM.RoleEnum;
import model.entity.User;
import model.entity.Vehicle;
import model.entity.exceptions.NoVehicleAssignException;
import model.entity.vehicles.Car;

import java.util.*;
import java.util.stream.Collectors;

import static model.entity.Main.log;

/**
 * stvara novog vozaca sa atributima User klase i novim jedinstvenim poljima
 */

public final class Driver extends User implements Rateable {
    private Set<Vehicle> vehicles=new HashSet<>();
    private int ratingCount=0;
    private double avgRating=0;
    private int seats;

    public Driver(String name,Vehicle vehicle,int seats){
        super(name);
        Vehicle auto=new Vehicle();
        vehicles.add(auto);
        this.seats=seats;
        setRole(RoleEnum.VOZAC);
        log.debug("Novi Driver objekt:"+vehicle+" "+seats+" "+name+" "+avgRating+" "+getRole());
    }
    public Driver(String name){
        super(name);
        Vehicle auto=new Vehicle();
        vehicles.add(auto);
        setRole(RoleEnum.VOZAC);
    }

    public Driver() {super();setRole(RoleEnum.VOZAC);}

    //LAMBDA U FILTERU I MAPI
    public <T extends Vehicle & Comparable<Vehicle>> void compareVehicles(){
        vehicles.stream().filter(v->v instanceof Car).map(v->(Car) v).sorted().collect(Collectors.toSet());
    }

    @Override
    public void getRating(int rating) {
        avgRating+=rating;
        ratingCount++;
    }

    public int getRatingCount() {return ratingCount;}

    protected double getAvgRatingRatio() {
        return avgRating/ratingCount;
    }
    public void setAvgRating(double rating) {avgRating = rating;}

    public double getAvgRating() {return avgRating;}

    public void setVehicles(User user, List<Vehicle> vehicles) {
        vehicles.stream().filter(v->v.getDriverId().equals(user.getId())).toList();
        Set<Vehicle> samoZaOvogDrivera= new HashSet<>(vehicles);
        ((Driver)user).vehicles=samoZaOvogDrivera;
    }

    /**
     * postavlja vozilo za vozaca
     * @param sc unos korisnika za model,boju i registraciju
     */
    public void setVehicle(Scanner sc){
        for(Vehicle v:vehicles){
            if(v==null){
                NoVehicleAssignException e=new NoVehicleAssignException("Vozilo nije postavljeno");
                log.error("Nepostavljeno vozilo"+" "+e);
                throw e;
            }
            v.setModel(sc.nextLine());
            v.setColor(sc.nextLine());
            v.setRegistration(sc.nextLine());
        }

    }

    public Set<Vehicle> getVehicles(){return vehicles;}

    /**
     * vraca odredeno vozilo
     * @param n id tog odredenog vozila
     * @return trazeno vozilo
     */

    public Optional<String> getVehicle(int n) {
        Vehicle to=null;
        for(Vehicle v:vehicles){
            if(n==v.getId()) {
                to = v;
            }
        }
        return Optional.ofNullable(to.getAll());
    }

    public int getSeats() {
        return seats;
    }

}
