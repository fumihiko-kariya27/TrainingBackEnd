package com.example.aspect.log;

public interface LogWriter {
	
	void debug(String message, Object ...args);
	
	void info(String message, Object ...args);
	
	void warn(String message, Object ...args);
	
	void err(String message, Object ...args);
}
