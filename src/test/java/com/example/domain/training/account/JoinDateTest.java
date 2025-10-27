package com.example.domain.training.account;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.TestExecutionListeners;

import com.example.domain.training.account.JoinDate;
import com.example.test.config.CustomTestConfigurationListener;

@TestExecutionListeners(
	listeners = { CustomTestConfigurationListener.class }, 
	mergeMode = TestExecutionListeners.MergeMode.MERGE_WITH_DEFAULTS
)
public class JoinDateTest {
	
	@Test
	@DisplayName("日付文字列からインスタンス生成できる")
	public void createFromString(){
		String in = "2025-10-01";
		String exp = "2025-10-01";
		
		assertThatCode(() -> {
			JoinDate actual = new JoinDate(in);
		})
		.as("例外が発生しないことを検証")
		.doesNotThrowAnyException();
		
		JoinDate actual = new JoinDate(in);
		assertThat(actual.toString()).as("JoinDateの文字列表現").isEqualTo(exp);
		assertThat(actual.toJson()).as("JSONの文字列表現").isEqualTo(exp);
	}
	
	@Test
	@DisplayName("日付インスタンスからインスタンスを生成できる")
	public void createFromLocalDate() {
		LocalDate in = LocalDate.of(2024, 01, 10);
		assertThatCode(() -> {
			JoinDate actual = new JoinDate(in);
		})
		.as("例外が投げられないことを確認")
		.doesNotThrowAnyException();
		
		String expFormat = in.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		
		JoinDate actual = new JoinDate(in);
		
		assertThat(actual.toString()).as("JoinDateの文字列表現").isEqualTo(expFormat);
		assertThat(actual.toJson()).as("JSONの文字列表現").isEqualTo(expFormat);
	}
	
	@Test
	@DisplayName("String初期化パラメータにnullを指定された場合例外を投げる")
	public void createdFromStringIsNull() {
		assertThatThrownBy(() -> {
			String in = null;
			JoinDate joinDate = new JoinDate(in);
		})
		.as("コンストラクタ引数にnull指定")
		.isInstanceOf(IllegalArgumentException.class)
		.hasMessage("日付にnullは指定できません");
	}
	
	@Test
	@DisplayName("LocalDate初期化パラメータにnullを指定された場合例外を投げる")
	public void createdFromLocalDateIsNull() {
		assertThatThrownBy(() -> {
			LocalDate in = null;
			JoinDate joinDate = new JoinDate(in);
		})
		.as("コンストラクタ引数にnull指定")
		.isInstanceOf(IllegalArgumentException.class)
		.hasMessage("日付にnullは指定できません");
	}
	
	@Test
	@DisplayName("yyyy-MM-ddの形式で日付が指定されてない場合例外を投げる")
	public void createdFromUnexpectedFormat() {
		String in = "20241001";
		assertThatThrownBy(() -> {
			JoinDate joinDate = new JoinDate(in);
		})
		.as("yyyyMMddの日付文字列を指定")
		.isNotInstanceOf(DateTimeParseException.class)
		.hasMessage(String.format("解析不可能な日付文字列です [%s]", in));
	}
}
