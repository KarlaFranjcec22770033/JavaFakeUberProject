package model.entity;

import model.entity.ENUM.RoleEnum;
import model.entity.JSON.Backup;
import model.entity.JSON.BackupData;
import model.entity.JSON.JsonFiles;
import model.entity.XML.JaxbFile;
import model.entity.exceptions.WrongInputException;
import model.entity.interfaces.Driver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Gatherers;

import static model.entity.ENUM.StatusEnum.NOVO;

public class Main {

    public static Logger log = LoggerFactory.getLogger(model.entity.Main.class);

    /**
     * pokrece program FakeUber
     */
    static void main(String[] args) {

        log.trace("Pokretanje Fakeuber aplikacije...");

        Scanner sc = new Scanner(System.in);
        System.out.println("Dobrodošli u FakeUber, najnoviji sustav rezervacije prijevoza. Ako Vam je potrebna vožnja upišite \"DA\", a ako ste vozac upisite \"VOZAC\"");
        String answer = sc.nextLine();
        answer = answer.toUpperCase();
        List<User> users = JsonFiles.readUsers("users.json");
        List<Booking> rezervacije=JsonFiles.readBooking("rezervacije.json");
        List<Vehicle> vozila=JsonFiles.readVehicle("vozila.json");

        BackupData bd1=new BackupData(users,rezervacije,vozila);
        try {
            if (!(answer.equals("DA") || answer.equals("VOZAC"))) {
                throw new WrongInputException(answer);
            }
        } catch (WrongInputException e) {
            log.warn("Neispravan unos! - " + answer + " " + e);
            System.out.println(e.getMessage() + " je neispravan unos, molimo upisite jedno od ponudenog: \"VOZAC\", \"DA\"");
            answer = sc.nextLine();
            answer = answer.toUpperCase();
        }
        if ("DA".equals(answer)) {
            JaxbFile.log("Korinik je unio DA");
            log.info("Korisnik zapocinje unos rezervacija");
            System.out.println("Vasi korisnici su:\n");
           users.stream().filter(u-> u.getRole().equals(RoleEnum.PUTNIK)).forEach(System.out::println);
            System.out.print("Zelite li dodati jos korisnika? ");
            String odgovor = sc.nextLine();
            while(odgovor.equalsIgnoreCase("DA")){
                JaxbFile.log("Korinik unosi extra korisnike");
                User novi=new Passanger();
                System.out.println("Upisite potrebne informacije(ime,ulogu,email): ");
                novi.setName(sc.nextLine());
                novi.setRole(RoleEnum.valueOf(sc.nextLine().toUpperCase()));
                novi.setEmail(sc.nextLine());
                users.add(novi);
                JsonFiles.writeList("users.json", users);
                System.out.print("Još korisnika? ");
                odgovor = sc.nextLine();
            }
            System.out.println("Vase rezervacije su:\n");
            for(User u : users){
                u.addBooking(u,rezervacije);
            }
            rezervacije.stream().forEach(System.out::println);
            System.out.print("Zelite li dodati jos rezervacija? ");
            odgovor = sc.nextLine();
            while(odgovor.equalsIgnoreCase("DA")){
                JaxbFile.log("Korinik unosi extra rezervacije");
                System.out.print("unesite userId za rezervacije: ");
                int odg=sc.nextInt();
                Booking novi=Booking.setBooking(sc,odg);
                rezervacije.add(novi);
                JsonFiles.writeList("rezervacije.json", rezervacije);
                System.out.print("Još korisnika? ");
                odgovor = sc.nextLine();
            }

            List<User> userLista = new ArrayList<>(users);
            userLista.sort(Comparator.comparing(User::getName));
            System.out.println("Sortirana lista po imenima korisnika:");
            userLista.forEach(u -> System.out.println(u.getName()));

            System.out.println("Zanima li vas najbliza voznja današnjem danu ili najkasnija(u budućnosti):");
            String answer3 = sc.nextLine();
            if (answer3.equalsIgnoreCase("najbliza")) {
                JaxbFile.log("Korinik trazi najblizu rezervaciju");
                log.info("Korisnik pretrazuje najblizu rezervaciju");
                var najbliza = MojeMetode.pronadiNajblizuVoznju(users);
                if (najbliza.isOk()) {
                    System.out.println(najbliza.getValue().get());
                }
            } else if (answer3.equalsIgnoreCase("najkasnija")) {
                JaxbFile.log("Korinik trazi najkasniju rezervaciju");
                log.info("Korisnik pretrazuje najdalju rezervaciju");
                var najdalja = MojeMetode.pronadiNajkasnijuVoznju(users);
                if (najdalja.isOk()) {
                    System.out.println(najdalja.getValue().get());
                }
            }
        } else if ("VOZAC".equals(answer)) {
            log.info("Korisnik zapocinje kao vozac");
            JaxbFile.log("Korinik je unio VOZAC");
            System.out.println("Vasi vozaci su:\n");
            users.stream().filter(u->u.getRole().equals(RoleEnum.VOZAC)).forEach(System.out::println);
            System.out.print("Zelite li dodati jos vozaca? ");
            String odgovor = sc.nextLine();
            while(odgovor.equalsIgnoreCase("DA")){
                JaxbFile.log("Korinik unosi extra vozace");
                System.out.println("Upisite potrebne informacije(ime,ulogu,rating): ");
                User novi=new Driver();
                novi.setName(sc.next());
                novi.setRole(RoleEnum.valueOf(sc.next()));
                ((Driver) novi).setAvgRating(sc.nextDouble());
                System.out.println("Unesite vozilo(model,boju i registraciju):");
                ((Driver) novi).setVehicles(novi,vozila);
                log.info("Korisnik upjesno registrira " + novi.getName());
                users.add(novi);
                JsonFiles.writeList("users.json", users);
                System.out.print("Još korisnika? ");
            }

            // FILTER SA LAMBDOM
            List<Driver> driverLista = users.stream()
                    .filter(u -> u instanceof Driver)
                    .map(u -> (Driver) u)
                    .collect(Collectors.toList());

            System.out.println("Sortirani vozaci po rateingu:");

            driverLista.sort(Comparator.comparing(Driver::getAvgRating));
            driverLista.forEach(u -> System.out.println(u.getName()));

            System.out.println("Vozac sa najboljim ratingom je: ");
            System.out.println(driverLista.getLast().getName());

            //GATHERERS
            var konRateing = driverLista.stream()
                    .map(Driver::getAvgRating)
                    .gather(Gatherers.
                            fold(() -> 0.0, (sum, elem) -> sum + elem))
                    .findFirst()//mora se uzeti neki broj, odnosno uzima rezultat fold-a
                    .orElse(0.0);

            double konAvgRate = konRateing / driverLista.size();
            System.out.println("Prosjecan rateing svih vozaca je: " + konAvgRate);

            List<Driver> driverLista2 = users.stream()
                    .filter(u -> u instanceof Driver)
                    .map(u -> (Driver) u)
                    .toList();

            //MAP,FLATMAP SA LAMBDOM
            var vozacAuto = driverLista2.stream()
                    .flatMap(d -> d.getVehicles().stream()//flatmap stvara zajednicki set svih vozila nabrojanih
                            .map(v -> Map.entry(v.getModel(), d.getName()))) //stvara mapu model-vozac
                    .collect(Collectors.groupingBy(
                            Map.Entry::getKey,//stvara listu tj grupira po key-value->model auta
                            Collectors.mapping(Map.Entry::getValue, Collectors.toSet())
                            //dodaje toj mapi 'vrijednosti', za odredeni model sprema imena koji ga posjeduju
                    ));
            vozacAuto.forEach((auto, vozac) -> System.out.println(auto + " -> " + vozac));
        }

        System.out.println("Zelite li maknuti neku od rezervacija?(DA/NE)");
        String odgovor=sc.nextLine();
        if (odgovor.equalsIgnoreCase("DA")) {
            System.out.println("Ime pod kojim je rezervacija:");
            String ime = sc.nextLine();
            for (User u : users) {
                if (u.getName().equals(ime)) {
                    for (Booking booking : u.getBookings()) {
                        System.out.println(booking.toString());
                    }
                    System.out.println("odaberite rezervaciju prema id-u");
                    int priv_id = sc.nextInt();
                    JaxbFile.log("Korinik mice rezervaciju "+ priv_id+" od "+ime);
                    u.removeBooking(u.getOGBookings(), priv_id);
                    System.out.println("Rezervacije nakon brisanja za " +u.getName()+":");
                   for(Booking booking : u.getBookings()) {
                       System.out.println(booking.toString());
                   }
                    System.out.println();
                }
            }

        }
        List<Booking> sviZajedno = new ArrayList<>();
        for (User u : users) {
            sviZajedno.addAll(u.getBookings());
        }

        //COLLECT SA LAMBDOM->PREDICATE
        var partitionedBookings = sviZajedno.stream()
                .collect(Collectors.partitioningBy
                        (b -> new BookingRecord(b).booking().getStatus() == NOVO));
        System.out.println("Broj buducih rezervacija je: " + partitionedBookings.get(true).

                size());
        System.out.println("Broj proslih rezervacija je: " + partitionedBookings.get(false).

                size());

        BackupData bd2=new BackupData(users,rezervacije,vozila);

        System.out.print("Zelite li vratiti originalne postavke(1) ili spremiti trenutne(2): ");
        int odg=sc.nextInt();
        sc.nextLine();
        if(odg==1){
            Backup.save(bd1);
            JaxbFile.log("Korinik ucitava pricuvnu kopiju");

        }else{
            Backup.save(bd2);
            JaxbFile.log("Korinik stvara pricuvnu kopiju");
        }
        System.out.print("Zelite li ispis logova?");
        odgovor=sc.nextLine();
        if(odgovor.equalsIgnoreCase("da")){JaxbFile.ispis();}

        System.out.println("Ako ste zavrsili, molimo unesite \"KRAJ\"");
        String unos = sc.nextLine();
        try {
            if (unos.equalsIgnoreCase("KRAJ")) {
                log.info("Korisnik zavrsava program");
                log.trace("Zavrsavanje aplikacije...");
                System.exit(0);
            } else {
                throw new WrongInputException(unos);
            }
        } catch (
                WrongInputException e) {
            log.warn("Neispravan unos! - " + unos + " " + e);
            System.out.println(e.getMessage() + " nije unos za kraj, molimo ponovite unos");
            unos = sc.nextLine();
        }

    }
}
    /* dodati spoj izmedu rezervacija i vozaca
    eventualno rucno dodavanje od strane vozaca

     */
