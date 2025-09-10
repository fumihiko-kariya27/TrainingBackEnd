package com.example.domain.training.account;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import com.fasterxml.jackson.annotation.JsonValue;

public class JoinDate {

	private final LocalDate joinDate;

	private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

	public JoinDate(String joinDate) {
		this.joinDate = LocalDate.parse(joinDate, formatter);
	}

	public JoinDate(LocalDate joinDate) {
		this.joinDate = joinDate;
	}
	
	@JsonValue
	public String toJson() {
		return this.joinDate.format(formatter);
	}
	
	@Override
	public String toString() {
		return this.joinDate.format(formatter);
	}
}
