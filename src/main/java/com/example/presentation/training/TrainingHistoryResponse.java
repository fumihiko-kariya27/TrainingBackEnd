package com.example.presentation.training;

import com.example.domain.training.code.EGender;
import com.example.domain.training.code.EMemberStatus;
import com.example.domain.training.course.EAttendance;
import com.example.domain.training.history.TrainingHistory;

import lombok.Getter;

@Getter
class TrainingHistoryResponse {
	
	private final int userId;
	
	private final String userName;
	
	private final EGender gender;
	
	private final EMemberStatus status;
	
	private final EAttendance attendance;
	
	private final String programCode;
	
	private final String collectName;
	
	private final String description;

	TrainingHistoryResponse(TrainingHistory history){
		this.userId = history.getUser().getId();
		this.userName = history.getUser().getName();
		this.gender = history.getUser().getGender();
		this.status = history.getUser().getStatus();
		this.attendance = history.getAttendance();
		this.programCode = history.getCourse().getCode();
		this.collectName = history.getCourse().getCollectName();
		this.description = history.getCourse().getDescription();
	}
}
