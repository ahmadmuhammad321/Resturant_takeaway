package com.cwiztech.takeaway.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


import com.cwiztech.takeaway.Model.Customer;

public interface customerRepository extends JpaRepository<Customer, Long> {
	
	@Query(value = "select * from TBLCUSTOMER where ISACTIVE='Y'", nativeQuery=true)
	public List<Customer> findActive();
	
	@Query(value = "select * from TBLCUSTOMER "
			+ "where (CUSTOMER_NAME like ?1 "
			+ "or CUSTOMER_EMAIL like ?1) "
			+ "and ISACTIVE='Y'", nativeQuery = true)
	public List<Customer> findBySearch(String search);

	@Query(value = "select * from TBLCUSTOMER " 
			+ "where CUSTOMER_NAME like ?1 "
			+ "or CUSTOMER_EMAIL like ?1 ", nativeQuery = true)
	public List<Customer> findAllBySearch(String search);

}
