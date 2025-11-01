package com.example.service.user.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.domain.training.account.TrainingUser;
import com.example.domain.training.account.repository.UserRepository;
import com.example.domain.training.code.EJoinFlag;
import com.example.service.user.UserService;

@Service
class UserServiceImpl implements UserService {
	
	@Autowired
	private UserRepository repository;

	/**
	 * 退会しているユーザーを含めた入会暦のある全てのユーザー情報を取得する
	 * 
	 * @return ユーザー一覧
	 */
	@Override
	public List<TrainingUser> getAllUser() {
		return this.repository.selectUsers();
	}

	/**
	 * 入会フラグが指定された値と等しいユーザー一覧を取得する
	 * 
	 * @param 入会フラグ
	 * @return 入会フラグが指定された値と等しいユーザー一覧
	 */
	@Override
	public List<TrainingUser> getUsers(EJoinFlag flag) {
		return this.repository.selectUsersJoinFlagEqual(flag);
	}

	@Override
	public TrainingUser getUserByName(String name) {
		return this.repository.selectByName(name);
	}

}
