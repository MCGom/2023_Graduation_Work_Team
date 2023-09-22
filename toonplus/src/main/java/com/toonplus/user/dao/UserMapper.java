package com.toonplus.user.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.toonplus.user.dto.User;

@Mapper
@Repository
public interface UserMapper {
	List<User> getUserList();

	int checkId(User user);

	int checkEmail(User user);

	int checkNickName(User user);

	void insertUser(User user);

	User login(User user);

	void updateUser(User user);

	}

