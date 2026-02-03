package model.entity;

import jakarta.json.bind.annotation.JsonbProperty;
import model.entity.ENUM.RoleEnum;
import model.entity.interfaces.Driver;
import jakarta.json.bind.annotation.JsonbSubtype;
import jakarta.json.bind.annotation.JsonbTransient;
import jakarta.json.bind.annotation.JsonbTypeInfo;
import java.math.BigDecimal;
import java.util.*;

/**
 * Stvara novog korisnika u aplikaciji
 */

@JsonbTypeInfo(
        key = "@type",
        value = {
                @JsonbSubtype(alias = "DRIVER", type = Driver.class),
                @JsonbSubtype(alias = "PASSANGER", type = Passanger.class)
        }
)
public abstract class User implements Comparable<User> {
    private static int nextId=1;


    protected String location;
    @JsonbProperty("role")
    private RoleEnum role;
    private int id;
    private String name;
    @JsonbTransient
    private String email;
    private String password;
    @JsonbTransient
    private List<Booking> bookings=new ArrayList<>();
    @JsonbTransient
    private List<BookingRecord> records=new ArrayList<>();
    private BigDecimal km;

    @Override
    public int compareTo(User other) {
        return Integer.compare(id, other.id);
    }

    public void addBooking(User user,List<Booking> bookings) {
        List<Booking> samoZaTogUsera=bookings.stream().filter(b->b.getUserId().equals(user.getId())).toList();
        user.bookings.addAll(samoZaTogUsera);
    }
    @Override
    public String toString() {
        return this.getId()+" "+this.getName()+" "+this.getRole()+" "+this.getLocation();
    }

    public void setRole(RoleEnum uloga){
        this.role=uloga;
    }
    public RoleEnum getRole() {
        return role;
    }

    /**
     * ispisuje potrebne informacije za korisnika
     */

    public void removeBooking(List<? super Booking> bookings, int i) {
        Iterator<?> iterator = bookings.iterator();
        Booking removed = null;
        while (iterator.hasNext()) {
            Object ovaj = iterator.next();
            Booking b = (Booking) ovaj;
            if (i == b.getId()) {
                removed = b;
                iterator.remove();
                break;
            }
        }


        if (removed != null) {
            Booking finalRemoved = removed;
            records.removeIf(r ->
                    r.booking().getId()== finalRemoved.getId())
            ;
        }
    }

    public User(String name) {
        this.name = name;
        this.id=nextId++;
    }
    public User(){this.id=nextId++;}

    /**
     * postavlja ime korsinika
     * @param sc unos imena sa konzole
     */
    public void setUser(Scanner sc){
        this.name=sc.nextLine();
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setId(int id) {
        this.id = id;
        if (id >= nextId) {
            nextId = id + 1;
        }
    }
    public int getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    protected String getEmail() {
        return email;
    }
    protected void setEmail(String email) {
        this.email = email;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public Booking getBookings(int n) {
        Booking to=null;
        for (Booking b : bookings) {
            if (b.getId() == n) {
                to=b;
            }
        }
        return to;
    }

    public BookingRecord getRecords(int n) {
        BookingRecord to=null;
            for(BookingRecord r:records) {
                if(r.booking().getId()==n){
                    to=r;
                }
        }
            return to;
    }

    protected List<Booking> getOGBookings(){
        return bookings;
    }

    protected List<Booking> getBookings() {
        List<Booking> sortBookings=new ArrayList<>();
        sortBookings.addAll(bookings);
        sortBookings.sort(Comparator.comparing(Booking::getDate));
        return sortBookings;
    }

}
