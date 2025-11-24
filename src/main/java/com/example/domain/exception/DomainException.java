package com.example.domain.exception;

// 業務処理で起きる可能性のある例外の基底クラス
public class DomainException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	private final String message;

	public DomainException(String message) {
		super(message);
		if(message == null || message.isBlank()) {
			throw new IllegalArgumentException("エラーメッセージには空白文字、もしくはnullを設定できません");
		}
		
		this.message = message;
	}
	
	@Override
	public String getMessage() {
		return this.message;
	}
}
