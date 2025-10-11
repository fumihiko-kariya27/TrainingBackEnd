package com.example.domain.account;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDate;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.example.domain.training.account.TrainingUser;

import lombok.AllArgsConstructor;
import lombok.Getter;

public class TrainingUserTest {
	
	// TrainingUserクラスのテストケース毎の初期化パラメータクラス
	@AllArgsConstructor
	@Getter
	private static class TestParam {
		private final int id;
		private final String name;
		private final LocalDate joinDate;
		private final String gender;
		private final String status;
		private final String joinFlag;
	}
	
	private static TestParam[] params = {
		// 正常・アクティブユーザー
		new TestParam(1, "test00", LocalDate.of(2025, 4, 1), "0", "BRNZ", "1"),
		// 正常・インアクティブユーザー
		new TestParam(2, "test11", LocalDate.of(2025, 4, 1), "1", "GOLD", "0"),
		// 異常・ID不正
		new TestParam(0, "test22", LocalDate.of(2025, 4, 1), "0", "SLVR", "1"),
		// 異常・ユーザー名不正(null)
		new TestParam(4, null, LocalDate.of(2025, 4, 1), "0", "SLVR", "1"),
		// 異常・ユーザー名不正(空文字)
		new TestParam(4, "", LocalDate.of(2025, 4, 1), "0", "SLVR", "1"),
		// 異常・ユーザー名不正(空白文字)
		new TestParam(4, " ", LocalDate.of(2025, 4, 1), "0", "SLVR", "1"),
		// 異常・ユーザー名不正(記号使用)
		new TestParam(4, "test00-", LocalDate.of(2025, 4, 1), "0", "SLVR", "1"),
		// 異常・ユーザー名不正(数字始まり)
		new TestParam(4, "0test", LocalDate.of(2025, 4, 1), "0", "SLVR", "1"),
	};
	
	private static String[] expStringfy = {
		"ユーザー番号[1] : test00",
		"ユーザー番号[2] : test11",
	};
	
	@Test
	@DisplayName("入会状態が有効であるユーザーを作成する")
	public void createActiveUser() {
		TestParam in = params[0];
		String expUserStringfy = expStringfy[0];
		
		TrainingUser actual = new TrainingUser(in.getId(), in.getName(), in.getJoinDate(), in.getGender(), in.getStatus(), in.getJoinFlag());
		
		assertThat(actual.toString()).isEqualTo(expUserStringfy);
		assertThat(actual.isActive()).isTrue();
	}
	
	@Test
	@DisplayName("入会状態が有効であるユーザーを作成する")
	public void createInactiveUser() {
		TestParam in = params[1];
		String expUserStringfy = expStringfy[1];
		
		TrainingUser actual = new TrainingUser(in.getId(), in.getName(), in.getJoinDate(), in.getGender(), in.getStatus(), in.getJoinFlag());
		
		assertThat(actual.toString()).isEqualTo(expUserStringfy);
		assertThat(actual.isActive()).isFalse();
	}
	
	@Test
	@DisplayName("ID不正でユーザーが作成された場合に例外を投げる")
	public void createInvalidIDUser(){
		TestParam in = params[2];
		assertThatThrownBy(() -> {
			new TrainingUser(in.getId(), in.getName(), in.getJoinDate(), in.getGender(), in.getStatus(), in.getJoinFlag());
		})
		.as("ユーザーIDに0を指定")
		.isInstanceOf(IllegalArgumentException.class)
		.hasMessage("ユーザーIDは1以上の整数を指定してください");
	}
	
	@Test
	@DisplayName("ユーザー名がnullで作成された場合に例外を投げる")
	public void createUserNameIsNull(){
		TestParam in = params[3];
		assertThatThrownBy(() -> {
			new TrainingUser(in.getId(), in.getName(), in.getJoinDate(), in.getGender(), in.getStatus(), in.getJoinFlag());
		})
		.as("ユーザー名にnullを指定")
		.isInstanceOf(IllegalArgumentException.class)
		.hasMessage("ユーザー名が指定されていません");
	}
	
	@Test
	@DisplayName("ユーザー名が空文字で作成された場合に例外を投げる")
	public void createUserNameIsEmpty(){
		TestParam in = params[4];
		assertThatThrownBy(() -> {
			new TrainingUser(in.getId(), in.getName(), in.getJoinDate(), in.getGender(), in.getStatus(), in.getJoinFlag());
		})
		.as("ユーザー名に空文字を指定")
		.isInstanceOf(IllegalArgumentException.class)
		.hasMessage("ユーザー名が指定されていません");
	}
	
	@Test
	@DisplayName("ユーザー名が空白文字で作成された場合に例外を投げる")
	public void createUserNameIsBlank(){
		TestParam in = params[5];
		assertThatThrownBy(() -> {
			new TrainingUser(in.getId(), in.getName(), in.getJoinDate(), in.getGender(), in.getStatus(), in.getJoinFlag());
		})
		.as("ユーザー名に空白文字を指定")
		.isInstanceOf(IllegalArgumentException.class)
		.hasMessage("ユーザー名が指定されていません");
	}
	
	@Test
	@DisplayName("ユーザー名に使用できない文字が含まれている場合に例外を投げる")
	public void createUserNameUnuseableChar(){
		TestParam in = params[6];
		assertThatThrownBy(() -> {
			new TrainingUser(in.getId(), in.getName(), in.getJoinDate(), in.getGender(), in.getStatus(), in.getJoinFlag());
		})
		.as("ユーザー名に空白文字を指定")
		.isInstanceOf(IllegalArgumentException.class)
		.hasMessage("ユーザー名は英数字のみ使用でき、英字始まりである必要があります");
	}
	
	@Test
	@DisplayName("ユーザー名が数字始まりである場合に例外を投げる")
	public void createUserNameStartWithNumber(){
		TestParam in = params[7];
		assertThatThrownBy(() -> {
			new TrainingUser(in.getId(), in.getName(), in.getJoinDate(), in.getGender(), in.getStatus(), in.getJoinFlag());
		})
		.as("ユーザー名に空白文字を指定")
		.isInstanceOf(IllegalArgumentException.class)
		.hasMessage("ユーザー名は英数字のみ使用でき、英字始まりである必要があります");
	}
}
