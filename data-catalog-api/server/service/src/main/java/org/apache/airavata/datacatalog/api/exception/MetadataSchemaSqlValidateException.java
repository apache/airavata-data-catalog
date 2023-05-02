package org.apache.airavata.datacatalog.api.exception;

public class MetadataSchemaSqlValidateException extends Exception {

    public MetadataSchemaSqlValidateException() {
    }

    public MetadataSchemaSqlValidateException(String message) {
        super(message);
    }

    public MetadataSchemaSqlValidateException(Throwable cause) {
        super(cause);
    }

    public MetadataSchemaSqlValidateException(String message, Throwable cause) {
        super(message, cause);
    }

    public MetadataSchemaSqlValidateException(String message, Throwable cause, boolean enableSuppression,
            boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
