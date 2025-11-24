package com.example.domain.exception.training;

import com.example.domain.exception.DomainException;

public class TrainingAlreadyRegisteredException extends DomainException {
	
	private static final long serialVersionUID = 1L;

	public TrainingAlreadyRegisteredException(String message) {
		super(message);
	}
}
