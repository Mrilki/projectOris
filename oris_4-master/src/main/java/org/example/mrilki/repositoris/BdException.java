package org.example.mrilki.repositoris;


public class BdException extends Exception {
    public BdException() {
        super();
    }

    public BdException(String message) {
        super(message);
    }

    public BdException(String message, Throwable cause) {
        super(message, cause);
    }

    public BdException(Throwable cause) {
        super(cause);
    }

    protected BdException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
