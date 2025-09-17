package com.example.presentation.training;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.domain.training.account.TrainingUser;
import com.example.domain.training.code.EJoinFlag;
import com.example.service.user.UserService;

@Controller
public class UserController {
	
	@Autowired
	private UserService service;
	
	@GetMapping("/user")
	public ResponseEntity<List<TrainingUser>> getAllUser(@RequestParam(value = "joinFlag", required = false) String joinFlag){
		List<TrainingUser> users = Collections.emptyList();
		if(joinFlag == null || joinFlag.isBlank()) {
			// 入会フラグが指定されてない場合は全ユーザを取得する
			users = this.service.getAllUser();
		}
		else {
			// 入会フラグが指定されている場合は入会フラグが指定された値と等しいユーザを取得する
			EJoinFlag flag = EJoinFlag.getByCode(joinFlag);
			users = this.service.getUsers(flag);
		}
		return ResponseEntity.ok(users);
	}
}
