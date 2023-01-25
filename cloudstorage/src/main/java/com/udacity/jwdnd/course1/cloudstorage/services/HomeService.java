package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.User;

public class HomeService {

	UserMapper userMapper;
	HashService hashService;
	EncryptionService encryptionService;

	public boolean isUsernameAvailable(String username) {
		return userMapper.getUser(username) == null;
	}

	
	public User getUser(String username) {
		return userMapper.getUser(username);
	}

	
}
