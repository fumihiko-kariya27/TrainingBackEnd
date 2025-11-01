package com.example.presentation.advice;

import java.util.HashMap;
import java.util.Map;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

/**
 * コントローラに対する共通処理を定義する
 */
@ControllerAdvice
@Aspect
@Slf4j
public class ControllerAspect {
	
	/**
	 * リクエスト元のIPアドレスをログ出力する
	 */
	@Before("execution(* com.example.presentation.*..*.*(..))")
	public void loggingSourceIp(JoinPoint joinPoint) {
		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		// AOPはアプリ起動中であっても発火する可能性があるため、リクエストがある場合にのみログ出力を行う
		if(attr == null) {
			return;
		}
		String requestIp = getClientIp(attr.getRequest());
		log.info("Method {} is called from {}", joinPoint.getSignature().toString(), requestIp);
	}
	
	/**
	 * リクエスト情報からクライアントのIPアドレスを取得する
	 */
	private String getClientIp(HttpServletRequest request) {
		// リクエストがフォワードングされている場合は元のIPアドレスを取得する
		String forwarded = request.getHeader("X-Forwarded-For");
		if(forwarded != null && !forwarded.isEmpty()) {
			return forwarded.split(",")[0].trim();
		}
		return request.getRemoteAddr();
	}
	
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
	
	@ExceptionHandler(value = {BadCredentialsException.class, UsernameNotFoundException.class, DisabledException.class, LockedException.class})
	public ResponseEntity<Map<String, String>> invalidCredentails(Exception e){
		log.error(e.getMessage());
		
		Map<String, String> cause = new HashMap<>();
		cause.put("message", "ユーザー名もしくはパスワードが正しくありません");
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(cause);
	}
	
	/**
	 * 補足されない例外が発生した場合、ステータスコード500でエラーを返却する
	 */
	@ExceptionHandler(value = Exception.class)
	public ResponseEntity<UnhandledError> handleUncatchedException(Exception e){
		log.error(e.getMessage());
		
		UnhandledError exception = new UnhandledError();
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(exception);
	}
}
