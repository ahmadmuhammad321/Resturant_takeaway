package com.cwiztech.takeaway.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


import com.cwiztech.takeaway.Model.Payment;

public interface paymentRepository extends JpaRepository<Payment, Long> {
	
	@Query(value = "select * from TBLPAYMENT where ISACTIVE='Y'", nativeQuery=true)
	public List<Payment> findActive();
	
	@Query(value = "select * from TBLPAYMENT "
			+ "where (PAYMENT_AMOUNT like ?1 "
			+ "or PAYMENT_TYPE like ?1) "
			+ "and ISACTIVE='Y'", nativeQuery = true)
	public List<Payment> findBySearch(String search);

	@Query(value = "select * from TBLPAYMENT " 
			+ "where PAYMENT_AMOUNT like ?1 "
			+ "or PAYMENT_TYPE like ?1 ", nativeQuery = true)
	public List<Payment> findAllBySearch(String search);

}
