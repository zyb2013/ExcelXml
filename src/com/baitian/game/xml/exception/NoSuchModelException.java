package com.baitian.game.xml.exception;

/**
 * 
 * @author zhuyuanbiao
 * @date 2015年11月24日 上午9:39:32
 *
 */
public class NoSuchModelException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6475847812720889761L;

	public NoSuchModelException() {
		super();
	}

	public NoSuchModelException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public NoSuchModelException(String message, Throwable cause) {
		super(message, cause);
	}

	public NoSuchModelException(String message) {
		super(message);
	}

	public NoSuchModelException(Throwable cause) {
		super(cause);
	}

}
