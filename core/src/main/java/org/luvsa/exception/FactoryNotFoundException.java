package org.luvsa.exception;

/**
 * @author Aglet
 * @create 2022/7/13 11:07
 */
public class FactoryNotFoundException extends Exception {
	public FactoryNotFoundException() {
	}

	public FactoryNotFoundException(String message) {
		super(message);
	}

	public FactoryNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public FactoryNotFoundException(Throwable cause) {
		super(cause);
	}

	public FactoryNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
