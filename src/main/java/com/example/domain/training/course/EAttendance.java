package com.example.domain.training.course;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.Getter;

// 受講区分
public enum EAttendance {
	// 未受講
	NOT_YET("NOT") {
		@Override
		public boolean isCompleted() {
			return false;
		}
	},
	
	// 受講中
	IN_PROCESS("PRC") {
		@Override
		public boolean isCompleted() {
			return false;
		}
	},
	
	// 受講完了
	COMPLETED("CMP") {
		@Override
		public boolean isCompleted() {
			return true;
		}
	};
	
	@Getter
	private String code;
	
	private static final Map<String, EAttendance> codeToEnum = Arrays.asList(values()).stream().collect(Collectors.toUnmodifiableMap(e -> e.getCode(), e -> e));
	
	private EAttendance(String code) {
		this.code = code;
	}
	
	public abstract boolean isCompleted();
	
	public static EAttendance getByCode(String code) {
		return Optional.ofNullable(codeToEnum.get(code)).orElseThrow(() -> {
			String message = String.format("受講区分が有効な値ではありません　[受講区分 : %s]", code);
			throw new IllegalArgumentException(message);
		});
	}
}
