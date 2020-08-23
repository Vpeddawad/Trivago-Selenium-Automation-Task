package com.base.config.error;

public class ConfigurationError extends TestRunnerError {

    /**
     * 
     */
    public ConfigurationError() {
        super();
    }

    /**
     * @param message
     * @param cause
     */
    public ConfigurationError(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * @param message
     */
    public ConfigurationError(String message) {
        super(message);
    }

    /**
     * @param cause
     */
    public ConfigurationError(Throwable cause) {
        super(cause);
    }

}
