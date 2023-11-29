package com.toonplus.user.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.toonplus.user.dao.UserMapper;
import com.toonplus.user.dto.User;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl {
	
	@Autowired
	private UserMapper userMapper;

	public List<User> getUserList() {
		return userMapper.getUserList();
	}

	public int checkId(User user) {
		return userMapper.checkId(user);
	}

	public int checkEmail(User user) {
		return userMapper.checkEmail(user);
	}

	public int checkNickName(User user) {
		return userMapper.checkNickName(user);
	}

	public void insertUser(User user) {
		userMapper.insertUser(user);
	}

	public void insertcount(User user){userMapper.insertcount(user);}

	public User login(User user) {
		return userMapper.login(user);
	}

	public void updateUser(User user) {
		userMapper.updateUser(user);
	}

	public User getUser(String user_id)
	{
		return userMapper.selectUser(user_id);
	}

	public void setFavorites(String user_id, String favorites)
	{
		userMapper.updateFavorite(user_id, favorites);
	}
	public void setLikes(String user_id, String likes)
	{
		userMapper.updateLike(user_id, likes);
	}


}
