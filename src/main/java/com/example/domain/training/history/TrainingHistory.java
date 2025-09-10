package com.example.domain.training.history;

import com.example.domain.training.account.TrainingUser;
import com.example.domain.training.course.EAttendance;
import com.example.domain.training.course.ProgramCourse;

import lombok.Data;

@Data
public class TrainingHistory {
	// ユーザー
	private TrainingUser user;
	// 受講状況
	private EAttendance attendance;
	// プログラム情報
	private ProgramCourse course;
}
