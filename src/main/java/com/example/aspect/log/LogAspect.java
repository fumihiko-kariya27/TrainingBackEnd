package com.example.aspect.log;

import java.util.Map;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LogAspect {
	
	@Autowired
	private LogWriter logWriter;
	
	@Pointcut("execution(* com.example.presentation.auth.LoginController.authenticateUser(..))")
	public void login() {};
	
	@Before("login()")
	public void beforeLogin(JoinPoint joinPoint) {
		this.logWriter.info("Login attempt method=%s", joinPoint.getSignature().toString());
	}
	
	@AfterReturning(pointcut = "login()", returning = "response")
	public void afterLoginSuccess(JoinPoint joinPoint, ResponseEntity<Map<String, String>> response) {
		String successUser = response.getBody().getOrDefault("user", "Unknown");
		this.logWriter.info("Login successed method=%s user=%s", joinPoint.getSignature().toString(), successUser);
	}
	
	@AfterThrowing(pointcut = "login()", throwing = "ex")
	public void afterLoginFailure(JoinPoint joinPoint, Exception ex) {
		this.logWriter.warn("Login failed method=%s cause=%s", joinPoint.getSignature().toString(), ex.getLocalizedMessage());
	}
}
