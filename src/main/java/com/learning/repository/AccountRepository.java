package com.learning.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.learning.entity.Account;

@Repository
public interface AccountRepository extends CrudRepository<Account, Long>{
	public Boolean existsByAccNumber(Long accNumber);
	public List<Account> findByUserId(Long id);
	public Account findByAccNumberAndAccCcy(Long accNumber, String accCcy);
	public Account findByAccNumber(Long accNumber);
}
