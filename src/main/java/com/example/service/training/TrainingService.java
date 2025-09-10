package com.example.service.training;

import java.util.List;

import com.example.domain.training.account.TrainingUser;
import com.example.domain.training.code.EJoinFlag;
import com.example.domain.training.history.TrainingHistory;

public interface TrainingService {
	
	List<TrainingUser> getAllUsers();
	
	List<TrainingUser> getUsers(EJoinFlag flag);
	
	List<TrainingHistory> getHistories(String userId);
}
