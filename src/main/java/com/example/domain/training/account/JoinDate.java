package com.example.domain.training.account;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import com.fasterxml.jackson.annotation.JsonValue;

public class JoinDate {

	private final LocalDate date;

	private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

	public JoinDate(String date) {
		this.date = LocalDate.parse(date, formatter);
	}

	public JoinDate(LocalDate date) {
		this.date = date;
	}

	@JsonValue
	public String toJson() {
		return this.date.format(formatter);
	}

	@Override
	public String toString() {
		return this.date.format(formatter);
	}
}
