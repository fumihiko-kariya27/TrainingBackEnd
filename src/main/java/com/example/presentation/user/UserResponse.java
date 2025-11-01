package com.example.presentation.user;

import com.example.domain.training.account.JoinDate;
import com.example.domain.training.account.TrainingUser;
import com.example.domain.training.code.EGender;
import com.example.domain.training.code.EJoinFlag;
import com.example.domain.training.code.EMemberStatus;

import lombok.Getter;

@Getter
class UserResponse {
	
	private final int id;
	private final String name;
	private final JoinDate joinDate;
	private final EGender gender;
	private EMemberStatus status;
	private EJoinFlag joinFlag;

	UserResponse(TrainingUser src){
		this.id = src.getId();
		this.name = src.getName();
		this.joinDate = src.getJoinDate();
		this.gender = src.getGender();
		this.status = src.getStatus();
		this.joinFlag = src.getJoinFlag();
	}
}
