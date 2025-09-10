package com.example.domain.training.account;

import java.time.LocalDate;

import com.example.domain.training.code.EGender;
import com.example.domain.training.code.EJoinFlag;
import com.example.domain.training.code.EMemberStatus;

import lombok.Getter;

// トレーニングサービスに登録されているユーザー情報
@Getter
public class TrainingUser {
	private final int id;
	private final String firstName;
	private final String lastName;
	private final JoinDate joinDate;
	private final EGender gender;
	private EMemberStatus status;
	private EJoinFlag joinFlag;

	public TrainingUser(Integer id, String firstName, String lastName, LocalDate joinDate, String gender,
			String status, String joinFlag) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.joinDate = new JoinDate(joinDate);
		this.gender = EGender.getByCode(gender);
		this.status = EMemberStatus.getByCode(status);
		this.joinFlag = EJoinFlag.getByCode(joinFlag);
	}

	@Override
	public String toString() {
		return String.format("ユーザー番号[%d] : %s %s", this.id, this.firstName, this.lastName);
	}

	/**
	 * ユーザーが入会しているか判断する
	 * 入会したが、既に退会している場合はfalseと判定する
	 * 
	 * @return 入会している[ true ] / 退会している [ false ]
	 */
	public boolean isActive() {
		return this.joinFlag.isActive();
	}
}
