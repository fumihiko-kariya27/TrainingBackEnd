package com.example.domain.training.account;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import com.fasterxml.jackson.annotation.JsonValue;

public class JoinDate {

	private final LocalDate date;

	private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

	public JoinDate(String date) {
		if(date == null) {
			throw new IllegalArgumentException("日付にnullは指定できません");
		}
		try {
			this.date = LocalDate.parse(date, formatter);
		} catch (DateTimeParseException e) {
			String message = String.format("解析不可能な日付文字列です [%s]", date);
			throw new IllegalArgumentException(message);
		}
	}

	public JoinDate(LocalDate date) {
		if(date == null) {
			throw new IllegalArgumentException("日付にnullは指定できません");
		}
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
