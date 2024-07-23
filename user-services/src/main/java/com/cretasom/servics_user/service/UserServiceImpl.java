package com.cretasom.servics_user.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
	Logger logger = LoggerFactory.getLogger(getClass());

	public User addUser(User user) {
		// TODO Auto-generated method stub
		user.setId(UUID.randomUUID().toString());
		try {
			mqtGateway.senToMqtt(user.getId(), "userIdTopic");
		} catch (Exception ex) {
			logger.warn("cannot sent message to mqtt");
		}
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
