package com.example.presentation.training;

import java.time.LocalDate;

import com.example.domain.training.course.ProgramCourse;

import lombok.Getter;

@Getter
class ProgramCourseResponse {
	
	private final String code;
	
	private final String collectName;
	
	private final String description;
	
	private final LocalDate applyStartDate;
	
	private final LocalDate applyEndDate;

	ProgramCourseResponse(ProgramCourse course){
		this.code = course.getCode();
		this.collectName = course.getCollectName();
		this.description = course.getDescription();
		this.applyStartDate = course.getApplyStartDate();
		this.applyEndDate = course.getApplyEndDate();
	}
}
