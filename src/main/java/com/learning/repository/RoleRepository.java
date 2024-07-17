package com.learning.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.learning.entity.Role;

@Repository
public interface RoleRepository extends CrudRepository<Role, Long>{
	Role findByName(String roleName);
}
