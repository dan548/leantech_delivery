package ai.leantech.delivery.exception;

public class NoSuchOrderStatusException extends Exception {

    public NoSuchOrderStatusException(String message) {
        super(message);
    }
}
