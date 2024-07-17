package com.learning.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.learning.entity.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long>{
	User findUserByUsername(String username);	
    Optional<User> findFirstByUsernameAndIsActived(String username, Boolean isActived);
}
