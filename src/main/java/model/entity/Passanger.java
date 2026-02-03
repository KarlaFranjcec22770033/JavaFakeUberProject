package model.entity;

import model.entity.ENUM.RoleEnum;
import model.entity.interfaces.Locateable;

import static model.entity.Main.log;

/**
 * stvara novog putnika
 */

public class Passanger extends User implements Locateable {

    private String phone;
    public Passanger(String name,String phone) {
        super(name);
        this.phone=phone;
        setRole(RoleEnum.PUTNIK);
    }
    public Passanger() {
        super();
        setRole(RoleEnum.PUTNIK);
    }

    public String getPhone() {
        return phone;
    }

    @Override
    public void viewLocation() {
        getLocation();
    }
    @Override
    public void updateLocation(String newLocation) {
        log.debug("Nova lokacija->"+newLocation);
        location=newLocation;
    }
   }
