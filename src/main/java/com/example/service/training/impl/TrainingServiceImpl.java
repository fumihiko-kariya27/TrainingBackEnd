package com.example.service.training.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.domain.training.course.ProgramCourse;
import com.example.domain.training.course.repository.TrainingRepository;
import com.example.domain.training.history.TrainingHistory;
import com.example.service.training.TrainingService;

@Service
@Transactional(rollbackFor = Exception.class)
class TrainingServiceImpl implements TrainingService {
	
	@Autowired
	private TrainingRepository repository;
	
	/**
	 * トレーニング一覧を取得する
	 * 適用期間が本日時点で有効でないものも取得対象とする
	 */
	@Override
	public List<ProgramCourse> getTrainingCourse() {
		return this.repository.selectCourse();
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
