package model;

public class IntervalTooSmallException extends IllegalArgumentException {
    public IntervalTooSmallException(String message) {
        super(message);
    }
}
