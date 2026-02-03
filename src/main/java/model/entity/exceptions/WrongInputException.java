package model.entity.exceptions;

import java.io.IOException;

/**
 * izbacuje se u slucaju pogresnog unosa sa konzole
 */

public class WrongInputException extends IOException {
    public WrongInputException(String message) {
        super(message);
    }
}
