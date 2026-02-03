package model.entity;
import jakarta.json.bind.annotation.JsonbDateFormat;
import model.entity.ENUM.StatusEnum;
import model.entity.exceptions.PastDateBookingException;
import jakarta.json.bind.annotation.JsonbTransient;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

import static model.entity.Main.log;

/**
 * stvara novu rezervaciju
 */

public class Booking implements Serializable {

    private static int nextId=1;
    private int id;
    private StatusEnum status;
    private String start;
    private String end;
    private String date;
    @JsonbTransient
    private static User user;
    private BigDecimal price;
    private BigDecimal distance;
    private Vehicle vehicle;
    private int userId;

    public Booking() {}

    private Booking(Builder builder) {
        this.id = builder.id;
        this.start = builder.start;
        this.end = builder.end;
        this.date = builder.date;
        this.user = builder.user;
        this.price = builder.price;
        this.distance = builder.distance;
        this.vehicle = builder.vehicle;
        this.status = builder.status;
        this.userId=builder.userId;
    }

    /**
     * instancira objekt rezervacije uz pomoc Builder patterna uz postepeno i ulancano postavljanje atributa
     */

    public static class Builder {
        private StatusEnum status;

        private int id;
        private String start;
        private String end;
        private String date;
        private User user;
        private BigDecimal price;
        private BigDecimal distance;
        private Vehicle vehicle;
        private Integer userId;

        /**
         * postavlja id rezervacije
         * @return trenutna instanca buildera
         */

        public Builder id() {
            this.id = nextId++;
            return this;
        }
        public Builder userId(int userId) {
            this.userId = userId;
            return this;
        }

        /**
         * postavlja pocetnu lokaciju rezervacije
         * @param start pocetna lokacija
         * @return trenutna instanca buildera
         */

        public Builder start(String start) {
            this.start = start;
            return this;
        }

        /**
         * postavlja zavrsnu lokaciju rezervacije
         * @param end zavrsna lokacija
         * @return trenutna instanca buildera
         */

        public Builder end(String end) {
            this.end = end;
            return this;
        }

        /**
         * postavlja datum rezervacije
         * @param date datum rezervacije
         * @return trenutna instanca buildera
         */

        public Builder date(LocalDateTime date) {
            this.date = date.format(DateTimeFormatter.ofPattern("dd.MM.yyyy. HH:mm"));

            if(date.isBefore(LocalDateTime.now())) {
                status=StatusEnum.STARO;
            }else if(date.isAfter(LocalDateTime.now())) {
                status=StatusEnum.NOVO;
            }else{
                status=StatusEnum.UTIJEKU;
            }
            return this;
        }

        /**
         * postavlja korisnika rezervacije
         * @param user korisnik unesen za tu rezervaciju
         * @return trenutna instanca buildera
         */

        public Builder user(User user) {
            this.user = user;
            return this;
        }

        /**
         * postavlja cijenu rezervacije
         * @param price cijena rezervacije
         * @return trenutna instanca buildera
         */

        public Builder price(BigDecimal price) {
            this.price = price;
            return this;
        }

        /**
         * postavlja duzinu rezervacije
         * @param distance duzina(km)
         * @return trenutna instanca buildera
         */

        public Builder distance(BigDecimal distance) {
            this.distance = distance;
            return this;
        }

        /**
         * postavlja vozilo za rezervacije
         * @param vehicle odabrano vozilo
         * @return trenutna instanca buildera
         */
        public Builder vehicle(Vehicle vehicle) {
            this.vehicle = vehicle;
            return this;
        }

        /**
         *zavrsava gradnju i vraca novi Booking objekt
         * @return novi objekt Booking
         */

        public Booking build() {
            return new Booking(this);
        }
    }
    public void setUserId(Integer userId) {
        this.userId=userId;
    /**
     * popunjava rezervaciju za danog korisnika sa informacijama unosa
     * moze baciti {@link PastDateBookingException}
     * @param sc trazene informacije (pocetna i zavrsna adresa,datum,odabir vozila) kroz unos korisnika
     * @param user specificni korisnik
     * @return vraca novostvorenu rezervaciju
     */
    }
        public static Booking setBooking(Scanner sc, int userId) {
                    System.out.println("Unesite početnu i završnu adresu, datum vožnje(dd.mm.gggg. HH:mm) te odabir vozila (1/2/3):");
                    String start = sc.nextLine();
                    String end = sc.nextLine();
                    String dateS = sc.nextLine();
                    LocalDateTime date = LocalDateTime.parse(dateS, DateTimeFormatter.ofPattern("dd.MM.yyyy. HH:mm"));
                try{
                    if (date.isBefore(LocalDateTime.now())) {
                        throw new PastDateBookingException(dateS);
                    }
                } catch (PastDateBookingException e) {
                    log.warn("Neuspjesan unos - voznja u proslosti"+" "+e);
                    System.out.println("Ne mozete zakazati rezervaciju u proslosti, molimo ponovite unos sa ispravnim datumom");
                    return setBooking(sc, userId);
                }


            int vehicleId = sc.nextInt();
            Vehicle vehicle;

            if (vehicleId == 1) {
                vehicle = new Vehicle("Mercedes", "crna", "ZG");
            } else if (vehicleId == 2) {
                vehicle = new Vehicle("BMW", "crvena", "KR");
            } else {
                vehicle = new Vehicle("Renault", "siva", "VZ");
            }
            sc.nextLine();

            log.debug("Novi Booking objekt sa startom: " + start + " i datumom: " + date);

            return new Builder()
                    .id()
                    .user(user)
                    .start(start)
                    .end(end)
                    .date(date)
                    .vehicle(vehicle)
                    .build();
        }

       /* public LocalDateTime getDate() {
            return LocalDateTime.parse(
                    date,
                    DateTimeFormatter.ofPattern("dd.MM.yyyy. HH:mm")
            );
        }

        */

    public String getDate(){
        return this.date;
    }
    public Integer getUserId(){return userId;}

        public void setId(Integer id) {
            this.id = id;
            if (id >= nextId) {
                nextId = id + 1;
            }
        }
        public void setStart(String start) {
            this.start = start;
        }
        public void setEnd(String end) {this.end = end;}
        public void setDate(String date) {this.date = date;}
        public String getStart() {
                return start;
            }
        public void setStatus(StatusEnum status) {this.status = status;}
        public String getEnd() {
                return end;
            }
        public int getId(){return id;}
        public StatusEnum getStatus() {return status;}
        @Override
        public String toString() {
                return  "id=" + id + ", start=" + start + ", end=" + end + ", date=" + date+", korisnik="+userId;
        }
        }




