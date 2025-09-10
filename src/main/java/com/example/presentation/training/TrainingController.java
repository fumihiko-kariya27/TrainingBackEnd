package com.example.presentation.training;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.domain.training.account.TrainingUser;
import com.example.domain.training.code.EJoinFlag;
import com.example.domain.training.history.TrainingHistory;
import com.example.service.training.TrainingService;

@RestController
@RequestMapping("/training")
public class TrainingController {
	
	@Autowired
	private TrainingService service;
	
	@GetMapping("/user")
	public ResponseEntity<List<TrainingUser>> getAllUser(@RequestParam(value = "joinFlag", required = false) String joinFlag){
		List<TrainingUser> users = Collections.emptyList();
		if(joinFlag == null || joinFlag.isBlank()) {
			// 入会フラグが指定されてない場合は全ユーザを取得する
			users = this.service.getAllUsers();
		}
		else {
			// 入会フラグが指定されている場合は入会フラグが指定された値と等しいユーザを取得する
			EJoinFlag flag = EJoinFlag.getByCode(joinFlag);
			users = this.service.getUsers(flag);
		}
		return ResponseEntity.ok(users);
	}
	
	@GetMapping("/history")
	public ResponseEntity<List<TrainingHistory>> getHistories(@RequestParam("userId") String userId){
		List<TrainingHistory> histories = this.service.getHistories(userId);
		return ResponseEntity.ok(histories);
	}
}
