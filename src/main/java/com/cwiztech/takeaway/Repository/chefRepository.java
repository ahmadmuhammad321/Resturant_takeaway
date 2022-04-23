package com.cwiztech.takeaway.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


import com.cwiztech.takeaway.Model.Chef;

public interface chefRepository extends JpaRepository<Chef, Long> {
	
	@Query(value = "select * from TBLCHEF where ISACTIVE='Y'", nativeQuery=true)
	public List<Chef> findActive();
	
	@Query(value = "select * from TBLCHEF "
			+ "where (CHEF_NAME like ?1 "
			+ "or CHEF_USERNAME like ?1) "
			+ "and ISACTIVE='Y'", nativeQuery = true)
	public List<Chef> findBySearch(String search);

	@Query(value = "select * from TBLCHEF " 
			+ "where CHEF_NAME like ?1 "
			+ "or CHEF_USERNAME like ?1 ", nativeQuery = true)
	public List<Chef> findAllBySearch(String search);

}
