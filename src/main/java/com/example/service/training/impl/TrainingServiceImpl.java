package com.example.service.training.impl;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.domain.training.account.TrainingUser;
import com.example.domain.training.code.EJoinFlag;
import com.example.domain.training.history.TrainingHistory;
import com.example.service.training.TrainingService;

@Service
@Transactional(rollbackFor = Exception.class)
class TrainingServiceImpl implements TrainingService {
	
	@Autowired
	private TrainingServiceRepository repository;

	/**
	 * 退会しているユーザーを含めた入会暦のある全てのユーザー情報を取得する
	 * 
	 * @return ユーザー一覧
	 */
	@Override
	public List<TrainingUser> getAllUsers() {
		return Collections.unmodifiableList(this.repository.selectUsers());
	}

	/**
	 * 入会フラグが指定された値と等しいユーザー一覧を取得する
	 * 
	 * @param 入会フラグ
	 * @return 退会済みのユーザー一覧
	 */
	@Override
	public List<TrainingUser> getUsers(EJoinFlag flag) {
		return Collections.unmodifiableList(this.repository.selectUsersJoinFlagEqual(flag));
	}

	/**
	 * 指定されたユーザーのトレーニング履歴を取得する
	 * 未受講であるトレーニングは取得されない
	 * 
	 * @param ユーザーID
	 * @return トレーニング履歴
	 */
	@Override
	public List<TrainingHistory> getHistories(String userId) {
		return this.repository.selectTrainingHistory(userId);
	}

}
