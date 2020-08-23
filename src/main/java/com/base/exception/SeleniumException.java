package com.base.exception;

public class SeleniumException extends RuntimeException {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public SeleniumException()
    {
        super();
    }

    public SeleniumException(String exp)
    {
        super(exp);
    }
}
