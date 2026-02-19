package zwee;

/**
 * A custom exception class for the Zwee application.
 */
public class ZweeException extends RuntimeException {
    public ZweeException(String message) {
        super(message);
    }
}