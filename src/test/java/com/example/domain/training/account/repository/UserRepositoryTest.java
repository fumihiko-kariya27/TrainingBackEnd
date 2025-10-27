package com.example.domain.training.account.repository;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import com.example.domain.training.account.TrainingUser;
import com.example.domain.training.account.repository.UserRepository;
import com.example.domain.training.code.EJoinFlag;
import com.example.test.config.CustomRepositoryTest;

@CustomRepositoryTest
@Sql("UserRepositoryTest.sql")
public class UserRepositoryTest {
	
	@Autowired
	UserRepository repository;
	
	private static List<TrainingUser> users = List.of(
		new TrainingUser(1, "kariya", LocalDate.of(2024, 4, 10), "0", "SLVR", "1"),
		new TrainingUser(2, "shinoda", LocalDate.of(2024, 5, 21), "0", "BRNZ", "1"),
		new TrainingUser(3, "anada", LocalDate.of(2024, 6, 9), "1", "BRNZ", "0"),
		new TrainingUser(4, "iwata", LocalDate.of(2024, 10, 18), "0", "GOLD", "1"),
		new TrainingUser(5, "takahashi", LocalDate.of(2024, 8, 23), "0", "SLVR", "0"),
		new TrainingUser(6, "ookita", LocalDate.of(2024, 11, 19), "1", "GOLD", "1")
	);
	
	@Test
	@DisplayName("ユーザー一覧を取得する")
	public void getUsers() {
		
		List<TrainingUser> actual = repository.selectUsers();
		
		assertThat(actual).containsExactlyInAnyOrderElementsOf(users);
	}
	
	@Test
	@DisplayName("入会フラグが有効であるユーザー一覧を取得する")
	public void getActiveUser(){
		List<TrainingUser> actual = repository.selectUsersJoinFlagEqual(EJoinFlag.ACTIVE);
		
		List<TrainingUser> expActiveUsers = users.stream().filter(user -> user.isActive()).toList();
		assertThat(actual).containsExactlyInAnyOrderElementsOf(expActiveUsers);
	}
}
