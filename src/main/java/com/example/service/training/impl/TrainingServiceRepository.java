package com.example.service.training.impl;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.domain.training.account.TrainingUser;
import com.example.domain.training.code.EJoinFlag;
import com.example.domain.training.history.TrainingHistory;

@Mapper
public interface TrainingServiceRepository {
	
	List<TrainingUser> selectUsers();
	
	List<TrainingUser> selectUsersJoinFlagEqual(@Param("joinFlag") EJoinFlag joinFlag);
	
	List<TrainingHistory> selectTrainingHistory(@Param("userId") String userId);
}
