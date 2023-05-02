package org.apache.airavata.datacatalog.api.exception;

public class MetadataSchemaSqlParseException extends Exception {

    public MetadataSchemaSqlParseException() {
    }

    public MetadataSchemaSqlParseException(String message) {
        super(message);
    }

    public MetadataSchemaSqlParseException(Throwable cause) {
        super(cause);
    }

    public MetadataSchemaSqlParseException(String message, Throwable cause) {
        super(message, cause);
    }

    public MetadataSchemaSqlParseException(String message, Throwable cause, boolean enableSuppression,
            boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
