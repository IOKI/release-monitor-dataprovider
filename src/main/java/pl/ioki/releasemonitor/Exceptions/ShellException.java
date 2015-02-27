package pl.ioki.releasemonitor.Exceptions;


public class ShellException extends RuntimeException {
    public ShellException() {
    }

    public ShellException(String message) {
        super(message);
    }

    public ShellException(String message, Throwable cause) {
        super(message, cause);
    }

    public ShellException(Throwable cause) {
        super(cause);
    }

    public ShellException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
