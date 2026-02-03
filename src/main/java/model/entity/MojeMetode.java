package model.entity;

import java.util.Comparator;
import java.util.List;

/**
 * sadrzi static metode za filtriranje po nizovima
 */

public class MojeMetode
{
    /**
     * pronalazi najskoriju voznju u polju unesenih korisnika i njihovih rezervacija
     * @param users mapa unesenih korisnika
     */
    public static Rezultat<Booking,String> pronadiNajblizuVoznju(List<User> users) {
        var op= users.stream()
                .flatMap(u -> u.getBookings().stream())
                .min(Comparator.comparing(Booking::getDate));

        if(op.isPresent()) {
            return Rezultat.rezultatOK(op.get());
        }else{
            return Rezultat.rezultatNotOK("Nema rezervacija");
        }
    }

    /**
     * pronalazi najkasniju voznju u polju unesenih korisnika i njihovih rezervacija
     * @param users mapa unesenih korisnika
     */

    //LAMBDA U FLATMAPU
    public static Rezultat<Booking,String> pronadiNajkasnijuVoznju(List<User> users) {
        var op= users.stream()
                .flatMap(u -> u.getBookings().stream())
                .max(Comparator.comparing(Booking::getDate));

        if(op.isPresent()) {
            return Rezultat.rezultatOK(op.get());
        }else{
            return Rezultat.rezultatNotOK("Nema rezervacija");
        }
    }

}
