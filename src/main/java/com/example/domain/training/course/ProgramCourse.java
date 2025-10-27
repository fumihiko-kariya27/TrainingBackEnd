package com.example.domain.training.course;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

import lombok.Getter;

public class ProgramCourse {
	
	@Getter
	private final String code;
	@Getter
	private final String collectName;
	@Getter
	private final String description;
	@Getter
	private final LocalDate applyStartDate;
	@Getter
	private final LocalDate applyEndDate;
	
	public ProgramCourse(String code, String collectName, String description, LocalDate applyStartDate, LocalDate applyEndDate) {
		if(code == null || collectName == null || description == null || applyStartDate == null) {
			// プログラムが開始以降いつでも受けられる場合は適用終了日が設定されないため、nullチェックは行わない
			throw new NullPointerException("必須初期化パラメータにnullが含まれています");
		}
		
		if(applyEndDate != null && applyStartDate.isAfter(applyEndDate)) {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
			String msg = String.format("開始日が終了日より未来の日程で設定されています 開始日:%s, 終了日:%s", applyStartDate.format(formatter), applyEndDate.format(formatter));
			throw new IllegalArgumentException(msg);
		}
		
		this.code = code;
		this.collectName = collectName;
		this.description = description;
		this.applyStartDate = applyStartDate;
		this.applyEndDate =  applyEndDate != null ? applyEndDate : LocalDate.MAX;
	}
	
	/**
	 * 指定された日時が適用期間内であるか判定する
	 * 
	 * @param date
	 * @return 適用期間である[ true ] / 適用期間でない[ false ] 
	 */
	public boolean isAppliedAt(LocalDate date) {
		if(this.applyStartDate.isAfter(date)) {
			// 適用開始日が指定日以降である場合は適用期間外である
			return false;
		}
		return !this.applyEndDate.isBefore(date);
	}

	@Override
	public int hashCode() {
		return Objects.hash(code);
	}

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
		ProgramCourse other = (ProgramCourse) obj;
		return Objects.equals(code, other.code);
	}
	
	@Override
	public String toString() {
		return String.format("プログラムコード[%s] : %s", this.code, this.collectName);
	}
}
