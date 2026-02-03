package model.entity.exceptions;

import java.io.IOException;

/**
 * izbacuje se ako je datum rezervacije u proslosti
 */
public class PastDateBookingException extends IOException {
    public PastDateBookingException(String message) {
        super(message);
    }
}
