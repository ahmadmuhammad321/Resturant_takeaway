package com.cwiztech.takeaway.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


import com.cwiztech.takeaway.Model.Order;

public interface orderRepository extends JpaRepository<Order, Long> {
	
	@Query(value = "select * from TBLORDER where ISACTIVE='Y'", nativeQuery=true)
	public List<Order> findActive();
	
	@Query(value = "select * from TBLORDER "
			+ "where (ORDER_DATE like ?1 "
			+ "or PICKUP_DATE like ?1) "
			+ "and ISACTIVE='Y'", nativeQuery = true)
	public List<Order> findBySearch(String search);

	@Query(value = "select * from TBLORDER " 
			+ "where ORDER_DATE like ?1 "
			+ "or PICKUP_DATE like ?1 ", nativeQuery = true)
	public List<Order> findAllBySearch(String search);

}
