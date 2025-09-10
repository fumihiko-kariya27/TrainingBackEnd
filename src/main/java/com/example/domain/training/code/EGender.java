package com.example.domain.training.code;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public enum EGender {
	// 男性
	MAN("0", "男性"),
	// 女性
	WOMAN("1", "女性");

	private final String code;
	private final String name;

	private static final Map<String, EGender> codeToEnumMap = Arrays.stream(values())
			.collect(Collectors.toUnmodifiableMap(e -> e.code, e -> e));

	private EGender(String code, String name) {
		this.code = code;
		this.name = name;
	}

	@Override
	public String toString() {
		return this.name;
	}

	public static EGender getByCode(String code) {
		return Optional.ofNullable(codeToEnumMap.get(code)).orElseThrow(() -> {
			String message = String.format("指定された性別区分は有効ではありません [性別区分 : %s]", code);
			throw new IllegalArgumentException(message);
		});
	}
}
