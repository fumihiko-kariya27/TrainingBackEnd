package com.example.domain.training.course.repository;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import com.example.domain.training.course.ProgramCourse;
import com.example.test.config.CustomRepositoryTest;

@CustomRepositoryTest
public class TrainingRepositoryTest {
	
	@Autowired
	private TrainingRepository repository;
	
	@Test
	@Sql("TrainingRepositoryTest.sql")
	public void testSelectCourse() {
		
		List<ProgramCourse> exp = List.of(
			new ProgramCourse("AGILE", "Agile Development", "アジャイル開発の基礎研修です", LocalDate.of(2025, 4, 5), LocalDate.of(2025, 4, 15)),
			new ProgramCourse("AIINT", "AI Introduction", "AIの基礎を学ぶ研修です", LocalDate.of(2025, 5, 1), LocalDate.of(2025, 5, 20)),
			new ProgramCourse("AWSCL", "AWS Cloud Basics", "AWSクラウドの基礎研修です", LocalDate.of(2025, 2, 18), LocalDate.of(2025, 3, 10))
		);
		
		List<ProgramCourse> actual = repository.selectCourse();
		
		assertThat(actual).containsExactlyInAnyOrderElementsOf(exp);
	}
}
