package com.example.presentation.training;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.domain.training.history.TrainingHistory;
import com.example.service.training.TrainingService;

@RestController
public class TrainingController {
	
	@Autowired
	private TrainingService service;
	
	@GetMapping("/training/history")
	public ResponseEntity<List<TrainingHistory>> getHistories(@RequestParam("userId") String userId){
		List<TrainingHistory> histories = this.service.getHistories(userId);
		return ResponseEntity.ok(histories);
	}
}
