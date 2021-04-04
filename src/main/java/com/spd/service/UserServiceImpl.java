package com.spd.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.spd.dao.UserDAO;
import com.spd.entity.User;

@Service
public class UserServiceImpl implements UserService{
	@Autowired 
	private BCryptPasswordEncoder bCryptPasswordEncoder; 

	@Autowired 
	UserDAO userDAO;

	@Override
	public void registerUser(User user) {
		System.out.println(user.getPassword()); 
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword())); 
		System.out.println(user.getPassword()); 
		user.setActive(1); 
		userDAO.registerUser(user);
	}

}
