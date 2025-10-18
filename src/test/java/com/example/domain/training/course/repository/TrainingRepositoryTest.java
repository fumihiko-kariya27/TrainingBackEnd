package com.example.domain.training.course.repository;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.jdbc.Sql;

import com.example.domain.training.account.TrainingUser;
import com.example.domain.training.course.EAttendance;
import com.example.domain.training.course.ProgramCourse;
import com.example.domain.training.history.TrainingHistory;
import com.example.test.config.CustomRepositoryTest;
import com.example.test.config.CustomTestConfigurationListener;

@CustomRepositoryTest
@Sql("TrainingRepositoryTest.sql")
@TestExecutionListeners(
	listeners = { CustomTestConfigurationListener.class }, 
	mergeMode = TestExecutionListeners.MergeMode.MERGE_WITH_DEFAULTS
)
public class TrainingRepositoryTest {
	
	@Autowired
	private TrainingRepository repository;
	
	@Test
	@DisplayName("登録されているトレーニング一覧を取得する")
	public void testSelectCourse() {
		
		List<ProgramCourse> exp = List.of(
			new ProgramCourse("AGILE", "Agile Development", "アジャイル開発の基礎研修です", LocalDate.of(2025, 4, 5), LocalDate.of(2025, 4, 15)),
			new ProgramCourse("AIINT", "AI Introduction", "AIの基礎を学ぶ研修です", LocalDate.of(2025, 5, 1), LocalDate.of(2025, 5, 20)),
			new ProgramCourse("AWSCL", "AWS Cloud Basics", "AWSクラウドの基礎研修です", LocalDate.of(2025, 2, 18), LocalDate.of(2025, 3, 10))
		);
		
		List<ProgramCourse> actual = repository.selectCourse();
		
		assertThat(actual).containsExactlyInAnyOrderElementsOf(exp);
	}
	
	@Test
	@DisplayName("指定されたユーザーのトレーニング受講履歴を取得する")
	public void testSelectTrainingHistories() {
		TrainingUser expUser = new TrainingUser(1, "kariya", LocalDate.of(2024, 4, 10), "0", "SLVR", "1");
		TrainingHistory history1 = new TrainingHistory();
		history1.setUser(expUser);
		history1.setAttendance(EAttendance.COMPLETED);
		history1.setCourse(new ProgramCourse("AGILE", "Agile Development", "アジャイル開発の基礎研修です", LocalDate.of(2025, 4, 5), LocalDate.of(2025, 4, 15)));
		
		TrainingHistory history2 = new TrainingHistory();
		history2.setUser(expUser);
		history2.setAttendance(EAttendance.IN_PROCESS);
		history2.setCourse(new ProgramCourse("AWSCL", "AWS Cloud Basics", "AWSクラウドの基礎研修です", LocalDate.of(2025, 2, 18), LocalDate.of(2025, 3, 10)));
		
		List<TrainingHistory> exp = List.of(history1, history2);
		
		List<TrainingHistory> actual = repository.selectTrainingHistory("1");
		
		assertThat(actual).containsExactlyInAnyOrderElementsOf(exp);
	}
}
