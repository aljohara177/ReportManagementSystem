package com.example.reportmanagmentsystem.exceptions;

import org.hibernate.cache.spi.support.AbstractReadWriteAccess;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus
public class ItemNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public ItemNotFoundException(String message) {
        super(message);
    }

    public ItemNotFoundException(String message, Throwable throwable ) {
        super(message,throwable);
    }
}
