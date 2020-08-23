package com.base.config.error;

public class TestRunnerError extends Error {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

	public TestRunnerError() {
        super();
    }

	public TestRunnerError(String message) {
        super(message);
    }

	public TestRunnerError(Throwable cause) {
        super(cause);
    }

	public TestRunnerError(String message, Throwable cause) {
        super(message, cause);
    }

}
