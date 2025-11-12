package com.example.aspect.log.impl;

import org.springframework.stereotype.Component;

import com.example.aspect.log.LogWriter;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
class Slf4LogWriter implements LogWriter {

	@Override
	public void debug(String message, Object... args) {
		log.debug(message, args);
	}

	@Override
	public void info(String message, Object... args) {
		log.info(message, args);
	}

	@Override
	public void warn(String message, Object... args) {
		log.warn(message, args);
	}

	@Override
	public void err(String message, Object... args) {
		log.error(message, args);
	}
}
