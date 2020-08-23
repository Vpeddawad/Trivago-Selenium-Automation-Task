package com.base.exception;

public class FixtureException extends RuntimeException {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public FixtureException()
    {
        super();
    }

    public FixtureException(String exp)
    {
        super(exp);
    }
}
