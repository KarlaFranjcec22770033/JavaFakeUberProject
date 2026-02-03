package model.entity.exceptions;

/**
 * izbacuje se kod prevelikog broja rezervacija unesenih
 */

public class ExceededMaxExceotion extends RuntimeException {
    public ExceededMaxExceotion(String user) {
        super("Dostignut maksimalan broj rezervacija za "+user);
    }
}
