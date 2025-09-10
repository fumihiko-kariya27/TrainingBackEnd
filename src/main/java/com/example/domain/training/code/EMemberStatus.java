package com.example.domain.training.code;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public enum EMemberStatus {

	// ブロンズ会員
	BRONZ("BRNZ", "ブロンズ会員"),
	// シルバー会員
	SILVER("SLVR", "シルバー会員"),
	// ゴールド会員
	GOLD("GOLD", "ゴールド会員");

	// 会員コードは４文字でなければならない
	private static final int STATUS_CODE_LENGTH = 4;

	private final String code;
	private final String name;

	private static final Map<String, EMemberStatus> codeToEnumMap = Arrays.asList(values()).stream()
			.collect(Collectors.toUnmodifiableMap(e -> e.code, e -> e));

	private EMemberStatus(String code, String name) {
		if (code.length() != STATUS_CODE_LENGTH) {
			String message = String.format("会員コードは４文字でなければいけません [会員コード : %s]", code);
			throw new IllegalArgumentException(message);
		}
		this.code = code;
		this.name = name;
	}

	@Override
	public String toString() {
		return this.name;
	}

	public static EMemberStatus getByCode(String code) {
		return Optional.ofNullable(codeToEnumMap.get(code)).orElseThrow(() -> {
			String message = String.format("指定された会員区分は有効ではありません [会員区分 : %s]", code);
			throw new IllegalArgumentException(message);
		});
	}
}
