package exceptions;

public class UserInputException extends RuntimeException {
    private final String message;

    public UserInputException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
