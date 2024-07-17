package com.cretasom.servics_user.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cretasom.servics_user.config.MqttGateway;
import com.cretasom.servics_user.entity.User;
import com.cretasom.servics_user.repo.UserRepo;

@Service
public class UserServiceImpl {
	@Autowired
	UserRepo repo;
	@Autowired
	MqttGateway mqtGateway;

	public User addUser(User user) {
		// TODO Auto-generated method stub
		user.setId(UUID.randomUUID().toString());
		mqtGateway.senToMqtt(user.getId(), "userIdTopic");
		repo.save(user);
		return user;
	}

	public User getUser(String id) {
		Optional<User> user = repo.findById(id);
		return user.isPresent() ? user.get() : null;
	}

	public List<User> getAllUser() {
		return repo.findAll();
	}

}
