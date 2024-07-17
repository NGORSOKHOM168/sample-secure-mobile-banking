package com.learning.repository;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.learning.entity.Authority;

@Repository
public interface AuthorityRepository extends CrudRepository<Authority, Long>{
	
}
