package com.cretasom.servics_user.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.cretasom.servics_user.entity.User;

@Service
public class UserServiceImpl {

	List<User> userList = new ArrayList<>();
	int i = 1;

	public User addUser(User user) {
		// TODO Auto-generated method stub
		user.setId(i);
		i++;
		userList.add(user);
		return user;
	}

	public User getUser(int id) {
		// id 1,2,4
		for (User u : userList) {
			if (u.getId() == id) {
				return u;
			}
		}
		return null;
	}

	public List<User> getAllUser() {
		return userList;
	}

}
