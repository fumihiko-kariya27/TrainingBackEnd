package com.example.presentation.advice;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import lombok.extern.slf4j.Slf4j;

/**
 * コントローラに対する共通処理を定義する
 */
@ControllerAdvice
@Slf4j
public class ControllerAspect {
	
	/**
	 * 入力値の検証に失敗した場合に検証エラー内容を返却する
	 */
	@ExceptionHandler(value = MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String, String>> handleValidationError(MethodArgumentNotValidException e){
		log.error(e.getMessage());
		
		Map<String, String> validationErrors = new HashMap<>();
		e.getBindingResult().getFieldErrors().forEach(error -> validationErrors.put(error.getField(), error.getDefaultMessage()));
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(validationErrors);
	}
	
	/**
	 * ハンドリングされないパスのリクエストを受信した場合にNot Foundを返却する
	 */
	@ExceptionHandler(value = NoResourceFoundException.class)
	public ResponseEntity<Map<String, String>> noResourceFoundError(NoResourceFoundException e){
		log.error(e.getMessage());
		
		Map<String, String> cause = new HashMap<>();
		cause.put("method", e.getHttpMethod().name());
		cause.put("path", e.getResourcePath());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(cause);
	}
	
	/**
	 * 補足されない例外が発生した場合、ステータスコード500でエラーを返却する
	 */
	@ExceptionHandler(value = Exception.class)
	public ResponseEntity<UnhandledError> handleUncatchedException(Exception e){
		log.error(e.getMessage());
		
		UnhandledError exception = new UnhandledError(e);
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(exception);
	}
}
