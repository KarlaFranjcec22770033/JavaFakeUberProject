package model.entity.exceptions;

/**
 * izbacuje se ako je postavljeno vozilo null pointer
 */

public class NoVehicleAssignException extends RuntimeException {
    public NoVehicleAssignException(String message) {
        super(message);
    }
}
