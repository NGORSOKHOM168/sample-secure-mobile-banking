package com.learning.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.learning.entity.Transaction;

@Repository
public interface TransactionRepository extends CrudRepository<Transaction, Long>{
	@Query("SELECT t FROM Transaction t WHERE t.createdAt BETWEEN :startDate AND :endDate AND t.accNumber = :accNumber")
	List<Transaction> findTransactionsByCreatedAtRangeAndAccNumber(
			@Param("startDate") LocalDate startDate, 
			@Param("endDate") LocalDate endDate,
			@Param("accNumber") 
			Long accNumber
		);
}
