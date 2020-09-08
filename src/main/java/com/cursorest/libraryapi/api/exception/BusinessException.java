package com.cursorest.libraryapi.api.exception;

@SuppressWarnings("serial")
public class BusinessException extends RuntimeException {

	public BusinessException(String s) {
		super(s);
	}
}
