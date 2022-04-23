package com.cwiztech.takeaway.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


import com.cwiztech.takeaway.Model.Admin;

public interface adminRepository extends JpaRepository<Admin, Long> {
	
	@Query(value = "select * from TBLADMIN where ISACTIVE='Y'", nativeQuery=true)
	public List<Admin> findActive();
	
	@Query(value = "select * from TBLADMIN "
			+ "where (ADMIN_NAME like ?1 "
			+ "or ADMIN_EMAIL like ?1) "
			+ "and ISACTIVE='Y'", nativeQuery = true)
	public List<Admin> findBySearch(String search);

	@Query(value = "select * from TBLADMIN " 
			+ "where ADMIN_NAME like ?1 "
			+ "or ADMIN_EMAIL like ?1 ", nativeQuery = true)
	public List<Admin> findAllBySearch(String search);

}
