package com.example.service.user;

import java.util.List;

import com.example.domain.training.account.TrainingUser;
import com.example.domain.training.code.EJoinFlag;

public interface UserService {
	
	List<TrainingUser> getAllUser();
	
	List<TrainingUser> getUsers(EJoinFlag flag);
	
	TrainingUser getUserByName(String name);
}
