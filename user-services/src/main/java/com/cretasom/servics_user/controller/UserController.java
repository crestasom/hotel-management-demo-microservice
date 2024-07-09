package com.cretasom.servics_user.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cretasom.servics_user.entity.User;
import com.cretasom.servics_user.service.UserServiceImpl;

@RestController
@RequestMapping("/users")
public class UserController {

	public UserController() {
		System.out.println("Creating bean of User controller");
	}

	@Autowired
	private UserServiceImpl userService;

	@PostMapping("/")
	public User addUser(@RequestBody User user) {
		System.out.println(user);
		return userService.addUser(user);
	}

	@GetMapping("/get-all")
	public List<User> getAllUser() {
		return userService.getAllUser();
	}

	@GetMapping("/get/{id}")
	public User getUser(@PathVariable int id) {
		return userService.getUser(id);
	}
}
