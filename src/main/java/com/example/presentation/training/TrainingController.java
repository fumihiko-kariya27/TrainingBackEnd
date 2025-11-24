package com.example.presentation.training;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.domain.exception.training.TrainingAlreadyRegisteredException;
import com.example.domain.training.course.ProgramCourse;
import com.example.domain.training.history.TrainingHistory;
import com.example.service.training.TrainingService;

import jakarta.validation.Valid;

@RestController
public class TrainingController {
	
	@Autowired
	private TrainingService service;
	
	@GetMapping("/training")
	public ResponseEntity<List<ProgramCourseResponse>> getTrainingCourse(){
		List<ProgramCourse> course = this.service.getTrainingCourse();
		
		List<ProgramCourseResponse> response = course.stream().map(entry -> new ProgramCourseResponse(entry)).toList();
		return ResponseEntity.ok(response);
	}
	
	@GetMapping("/training/history")
	public ResponseEntity<List<TrainingHistoryResponse>> getHistories(@RequestParam("userId") String userId){
		List<TrainingHistory> histories = this.service.getHistories(userId);
		
		List<TrainingHistoryResponse> response = histories.stream().map(history -> new TrainingHistoryResponse(history)).toList();
		return ResponseEntity.ok(response);
	}
	
	@PostMapping("/training/create")
	public HttpStatus createTraining(@Valid @RequestBody TrainingCreateRequest request, BindingResult bindingResult) {
		if(bindingResult.hasErrors()) {
			return HttpStatus.BAD_REQUEST;
		}
		
		ProgramCourse course = new ProgramCourse(request);
		try {
			this.service.createNew(course);
		} catch(TrainingAlreadyRegisteredException e) {
			return HttpStatus.CONFLICT;
		}
		return HttpStatus.CREATED;
	}
}
