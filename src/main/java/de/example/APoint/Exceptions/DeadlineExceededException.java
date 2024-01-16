package de.example.APoint.Exceptions;

public class DeadlineExceededException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public DeadlineExceededException(String message) {
        super(message);
    }

    // You can add additional constructors, methods, or fields if needed
}