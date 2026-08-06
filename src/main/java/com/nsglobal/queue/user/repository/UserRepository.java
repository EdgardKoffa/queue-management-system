package com.nsglobal.queue.user.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.nsglobal.queue.user.entity.User;


public interface UserRepository extends JpaRepository<User, Long> {
	
	@Query("""
			SELECT u
			FROM User u
			JOIN FETCH u.role
			LEFT JOIN FETCH u.branch
			WHERE u.userName = :userName
			""")
	Optional<User> findByUserName( String userName);
	
	
	boolean existsByUserName(String userName);
	
	@EntityGraph(attributePaths = {"role","branch"})
	Optional<User> findById(Long id);
	
	@EntityGraph(attributePaths = {"role","branch"})
	List<User> findAll();
	
	@EntityGraph(attributePaths = {"role","branch"})
	boolean existsByRole_id(Long role_id);
	//List<OperatorStatisticsDto> getUserStatistics();

}
