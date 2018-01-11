package server.exception;

public class DaoDataException extends Exception {

    private static final long serialVersionUID = 5L;

    public DaoDataException() {
        super();
    }

    public DaoDataException(String message) {
        super(message);
    }

    public DaoDataException(Throwable throwable) {
        super(throwable);
    }

    public DaoDataException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
