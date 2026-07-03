package com.nsglobal.queue.user.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nsglobal.queue.user.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
	
	Optional<User> findByUserName( String userName);

}
