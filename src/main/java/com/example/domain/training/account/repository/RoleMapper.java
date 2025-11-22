package com.example.domain.training.account.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RoleMapper {

	List<String> selectRoleByUserId(int id);
}
