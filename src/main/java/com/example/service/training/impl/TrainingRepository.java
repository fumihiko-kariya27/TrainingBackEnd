package com.example.service.training.impl;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.domain.training.course.ProgramCourse;
import com.example.domain.training.history.TrainingHistory;

@Mapper
public interface TrainingRepository {
	
	List<ProgramCourse> selectCourse();
	
	List<TrainingHistory> selectTrainingHistory(@Param("userId") String userId);
}
