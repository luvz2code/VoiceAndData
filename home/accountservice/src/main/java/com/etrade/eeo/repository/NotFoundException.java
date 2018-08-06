package com.etrade.eeo.repository;

/**
 * Created by rayyar on 3/9/18.
 */
public class NotFoundException extends RuntimeException {

    private final Long id;
    private final String type;

    public NotFoundException(final String type, final long id) {
        super(type + " could not be found with id: " + id);
        this.id = id;
        this.type = type;
    }

}

