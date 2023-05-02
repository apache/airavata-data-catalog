package org.apache.airavata.datacatalog.api.sharing.exception;

public class SharingException extends Exception {

    public SharingException() {
    }

    public SharingException(String message) {
        super(message);
    }

    public SharingException(Throwable cause) {
        super(cause);
    }

    public SharingException(String message, Throwable cause) {
        super(message, cause);
    }

    public SharingException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
