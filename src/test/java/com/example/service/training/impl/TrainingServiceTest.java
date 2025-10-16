package com.example.service.training.impl;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.TestExecutionListeners;

import com.example.domain.training.account.TrainingUser;
import com.example.domain.training.course.EAttendance;
import com.example.domain.training.course.ProgramCourse;
import com.example.domain.training.course.repository.TrainingRepository;
import com.example.domain.training.history.TrainingHistory;
import com.example.test.config.CustomTestConfigurationListener;

@ExtendWith(MockitoExtension.class)
@TestExecutionListeners(
	listeners = { CustomTestConfigurationListener.class }, 
	mergeMode = TestExecutionListeners.MergeMode.MERGE_WITH_DEFAULTS
)
public class TrainingServiceTest {
	
	@InjectMocks
	TrainingServiceImpl service;
	
	@Mock
	TrainingRepository repository;
	
	private static List<ProgramCourse> programCourses = List.of(
		new ProgramCourse("T01", "サンプルトレーニングコース01", "テスト用のサンプルトレーニングコース01です", LocalDate.of(2024, 1, 1), LocalDate.of(2024, 12, 31)),
		new ProgramCourse("T02", "サンプルトレーニングコース02", "テスト用のサンプルトレーニングコース02です", LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 31))
	);
	
	@Test
	@DisplayName("トレーニングプログラム一覧を取得する")
	public void getProgramCourse() {
		when(repository.selectCourse()).thenReturn(programCourses);
		
		List<ProgramCourse> actual = service.getTrainingCourse();
		
		// ServiceはRepositoryから取得したリストをそのまま返す設計のため、入力値を期待値としても扱う
		assertThat(actual).isNotNull().containsExactlyElementsOf(programCourses);
	}
	
	@Test
	@DisplayName("トレーニング履歴一覧を取得する")
	public void getTrainingHistories() {
		String userId = "test00";
		
		TrainingHistory history1 = new TrainingHistory();
		history1.setUser(new TrainingUser(1, userId, LocalDate.of(2025, 4, 1), "0", "BRNZ", "1"));
		history1.setAttendance(EAttendance.IN_PROCESS);
		history1.setCourse(new ProgramCourse("T01", "サンプルトレーニングコース01", "テスト用のサンプルトレーニングコース01です", LocalDate.of(2024, 1, 1), LocalDate.of(2024, 12, 31)));
		
		TrainingHistory history2 = new TrainingHistory();
		history2.setUser(new TrainingUser(1, "test11", LocalDate.of(2025, 5, 1), "1", "GOLD", "0"));
		history2.setAttendance(EAttendance.COMPLETED);
		history2.setCourse(new ProgramCourse("T02", "サンプルトレーニングコース02", "テスト用のサンプルトレーニングコース02です", LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 31)));
		
		List<TrainingHistory> histories = List.of(history1, history2);
		
		when(repository.selectTrainingHistory(anyString())).thenReturn(histories);
		
		List<TrainingHistory> actual = service.getHistories(userId);
		
		// ServiceはRepositoryから取得したリストをそのまま返す
		assertThat(actual).isNotNull().containsExactlyElementsOf(histories);
		
		// Serviceが引数に何も加工をせずにRepositoryに渡していることを確認する
		ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
		verify(repository).selectTrainingHistory(captor.capture());
		assertThat(captor.getValue()).isEqualTo(userId);
	}
}
