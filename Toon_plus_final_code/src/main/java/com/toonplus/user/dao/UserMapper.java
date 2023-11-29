package com.toonplus.user.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
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

	void insertcount(User user);
	User login(User user);

	void updateUser(User user);

	User selectUser(String user_id);


	void updateFavorite(@Param("USER_ID") String user_id, @Param("FAVORITES") String favorites);
	void updateLike(@Param("USER_ID") String user_id, @Param("LIKES") String likes);


	}

