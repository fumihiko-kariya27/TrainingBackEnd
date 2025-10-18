package com.example.presentation.training;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.example.domain.training.course.ProgramCourse;
import com.example.service.training.TrainingService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(TrainingController.class)
public class TrainingControllerTest {
	
	@Autowired
	MockMvc mockMvc;
	
	@MockitoBean
	TrainingService service;
	
	@Autowired
	ObjectMapper objectMapper;
	
	private final List<ProgramCourse> trainings = List.of(
		new ProgramCourse("AGILE", "Agile Development", "アジャイル開発の基礎研修です", LocalDate.of(2025, 4, 5), LocalDate.of(2025, 4, 15)),
		new ProgramCourse("AIINT", "AI Introduction", "AIの基礎を学ぶ研修です", LocalDate.of(2025, 5, 1), LocalDate.of(2025, 5, 20)),
		new ProgramCourse("AWSCL", "AWS Cloud Basics", "AWSクラウドの基礎研修です", LocalDate.of(2025, 2, 18), LocalDate.of(2025, 3, 10))
	);
	
	@BeforeAll
	public void init() {
		when(service.getTrainingCourse()).thenReturn(trainings);
	}
	
	@Test
	@DisplayName("トレーニング情報をJSON形式で取得する")
	public void getTrainingAsJson() throws Exception {
		String expJson = objectMapper.writeValueAsString(trainings);
		
		String actualJson = mockMvc.perform(
			get("/training")
			.accept(MediaType.APPLICATION_JSON)
		)
		.andExpect(status().isOk())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON))
		.andReturn()
		.getResponse()
		.getContentAsString();
		
		assertThat(actualJson).isEqualTo(expJson);
	}
}
