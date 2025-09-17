package com.example.service.training;

import java.util.List;

import com.example.domain.training.course.ProgramCourse;
import com.example.domain.training.history.TrainingHistory;

public interface TrainingService {
	
	List<ProgramCourse> getTrainingCourse();
	
	List<TrainingHistory> getHistories(String userId);
}
