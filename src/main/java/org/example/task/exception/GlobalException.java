package org.example.task.exception;

public class GlobalException extends RuntimeException{
	public GlobalException(String message, Throwable cause) {
		super(message, cause);
	}
}
