package model.entity.interfaces;

/**
 * omogucuje mogucnost provjere i postavljanja lokacije
 */

public interface Locateable {
    /**
     * vraca lokaciju korisnika
     */
    void viewLocation();

    /**
     * postavlja novu lokaciju korisnika
     * @param location nova lokacija
     */
    void updateLocation(String location);
}
