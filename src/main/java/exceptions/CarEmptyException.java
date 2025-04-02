package exceptions;

public class CarEmptyException extends RuntimeException {
    private final String message;

    public CarEmptyException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
