package com.cwiztech.takeaway.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


import com.cwiztech.takeaway.Model.Menu;

public interface menuRepository extends JpaRepository<Menu, Long> {
	
	@Query(value = "select * from TBLMENU where ISACTIVE='Y'", nativeQuery=true)
	public List<Menu> findActive();
	
	@Query(value = "select * from TBLMENU "
			+ "where (PRICE like ?1 "
			+ "or FOOD_ID like ?1) "
			+ "and ISACTIVE='Y'", nativeQuery = true)
	public List<Menu> findBySearch(String search);

	@Query(value = "select * from TBLMENU " 
			+ "where PRICE like ?1 "
			+ "or FOOD_ID like ?1 ", nativeQuery = true)
	public List<Menu> findAllBySearch(String search);

}
