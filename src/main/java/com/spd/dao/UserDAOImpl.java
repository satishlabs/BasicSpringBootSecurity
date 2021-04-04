package com.spd.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.spd.entity.User;

@Repository
public class UserDAOImpl implements UserDAO{
	@Autowired 
	 private JdbcTemplate jdbcTemp; 

	@Override
	public void registerUser(User user) {
		String sql="insert into myusers values(?,?,?,?,?,?,?)"; 
		jdbcTemp.update(sql,user.getUsername(),user.getPassword(),user.getFirstname(),user.getLastname(),user.getEmail(),user.getPhone(),user.getActive());
	}

}
