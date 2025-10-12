package com.example.domain.training.history;

import java.util.Objects;

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
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		TrainingHistory other = (TrainingHistory) obj;
		return attendance == other.attendance && Objects.equals(course, other.course)
				&& Objects.equals(user, other.user);
	}
	@Override
	public int hashCode() {
		return Objects.hash(attendance, course, user);
	}
}
