package com.example.service.training.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.domain.training.history.TrainingHistory;
import com.example.service.training.TrainingService;

@Service
@Transactional(rollbackFor = Exception.class)
class TrainingServiceImpl implements TrainingService {
	
	@Autowired
	private TrainingRepository repository;

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
