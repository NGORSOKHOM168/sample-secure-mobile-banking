package com.learning.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.learning.entity.UserRole;

@Repository
public interface UserRoleRepository extends CrudRepository<UserRole,Long>{
	
}
