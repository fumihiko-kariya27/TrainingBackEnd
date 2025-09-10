package com.example.domain.training.code;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public enum EJoinFlag {

	ACTIVE("1"),
	INACTIVE("0");

	private final String code;

	private static final Map<String, EJoinFlag> codeToEnumMap = Arrays.asList(values()).stream()
			.collect(Collectors.toUnmodifiableMap(e -> e.code, e -> e));

	private EJoinFlag(String code) {
		this.code = code;
	}

	public boolean isActive() {
		return this == EJoinFlag.ACTIVE;
	}

	public static EJoinFlag getByCode(String code) {
		return Optional.ofNullable(codeToEnumMap.get(code)).orElseThrow(() -> {
			String message = String.format("指定されたフラグ値は有効な値ではありません [入会フラグ : %s]", code);
			throw new IllegalArgumentException(message);
		});
	}
}
