package org.netconfig.model;

public class BaseError extends IllegalArgumentException {
    public BaseError() {
        super();
    }

    public BaseError(String message) {
        super(message);
    }
}
