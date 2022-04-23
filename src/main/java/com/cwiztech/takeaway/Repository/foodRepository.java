package com.cwiztech.takeaway.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


import com.cwiztech.takeaway.Model.Food;

public interface foodRepository extends JpaRepository<Food, Long> {
	
	@Query(value = "select * from TBLFOOD where ISACTIVE='Y'", nativeQuery=true)
	public List<Food> findActive();
	
	@Query(value = "select * from TBLFOOD "
			+ "where (FOOD_NAME like ?1 "
			+ "or FOOD_QUANTITY like ?1) "
			+ "and ISACTIVE='Y'", nativeQuery = true)
	public List<Food> findBySearch(String search);

	@Query(value = "select * from TBLFOOD " 
			+ "where FOOD_NAME like ?1 "
			+ "or FOOD_QUANTITY like ?1 ", nativeQuery = true)
	public List<Food> findAllBySearch(String search);

}
