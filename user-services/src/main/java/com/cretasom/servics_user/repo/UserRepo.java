package com.cretasom.servics_user.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cretasom.servics_user.entity.User;

public interface UserRepo extends JpaRepository<User, String> {

}
