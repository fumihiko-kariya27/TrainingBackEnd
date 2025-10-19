package com.example.presentation.training;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.example.domain.training.account.TrainingUser;
import com.example.domain.training.course.EAttendance;
import com.example.domain.training.course.ProgramCourse;
import com.example.domain.training.history.TrainingHistory;
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
	
	@BeforeEach
	public void init() {
		when(service.getTrainingCourse()).thenReturn(trainings);
	}
	
	@Test
	@DisplayName("トレーニング情報をJSON形式で取得する")
	public void getTrainingAsJson() throws Exception {
		String actualJson = mockMvc.perform(
			get("/training")
			.accept(MediaType.APPLICATION_JSON)
		)
		.andExpectAll(
			status().isOk(),
			content().contentType(MediaType.APPLICATION_JSON)
		)
		.andDo(print())
		.andReturn()
		.getResponse()
		.getContentAsString();
		
		String expJson = objectMapper.writeValueAsString(trainings);
		assertThat(actualJson).isEqualTo(expJson);
	}
	
	@Test
	@DisplayName("トレーニング情報をXML形式で取得する")
	public void getTrainingAsXml() throws Exception {
		mockMvc.perform(
			get("/training")
			.accept(MediaType.APPLICATION_XML)
		)
		.andExpectAll(
			status().isOk(),
			content().contentTypeCompatibleWith(MediaType.APPLICATION_XML)
		)
		.andDo(print())
		.andReturn()
		.getResponse()
		.getContentAsString();
	}
	
	@Test
	@DisplayName("トレーニング受講履歴を取得する")
	public void getTrainingHistories() throws Exception {
		TrainingUser expUser = new TrainingUser(1, "kariya", LocalDate.of(2024, 4, 10), "0", "SLVR", "1");
		TrainingHistory history1 = new TrainingHistory();
		history1.setUser(expUser);
		history1.setAttendance(EAttendance.COMPLETED);
		history1.setCourse(new ProgramCourse("AGILE", "Agile Development", "アジャイル開発の基礎研修です", LocalDate.of(2025, 4, 5), LocalDate.of(2025, 4, 15)));
		
		TrainingHistory history2 = new TrainingHistory();
		history2.setUser(expUser);
		history2.setAttendance(EAttendance.IN_PROCESS);
		history2.setCourse(new ProgramCourse("AWSCL", "AWS Cloud Basics", "AWSクラウドの基礎研修です", LocalDate.of(2025, 2, 18), LocalDate.of(2025, 3, 10)));
		
		List<TrainingHistory> histories = List.of(history1, history2);
		when(service.getHistories(anyString())).thenReturn(histories);
		
		String actualJson = mockMvc.perform(
			get("/training/history")
			.param("userId", "dummy")
			.accept(MediaType.APPLICATION_JSON)
		)
		.andExpectAll(
			status().isOk(),
			content().contentType(MediaType.APPLICATION_JSON)
		)
		.andDo(print())
		.andReturn()
		.getResponse()
		.getContentAsString();
		
		String expJson = objectMapper.writeValueAsString(histories);
		assertThat(actualJson).isEqualTo(expJson);
	}
}
