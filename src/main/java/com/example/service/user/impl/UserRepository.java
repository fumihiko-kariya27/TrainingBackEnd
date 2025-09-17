package com.example.service.user.impl;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.domain.training.account.TrainingUser;
import com.example.domain.training.code.EJoinFlag;

@Mapper
public interface UserRepository {
	
	List<TrainingUser> selectUsers();
	
	List<TrainingUser> selectUsersJoinFlagEqual(@Param("joinFlag") EJoinFlag joinFlag);
}
