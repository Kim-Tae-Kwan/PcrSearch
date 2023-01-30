package com.ktk.pcrSearch.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ktk.pcrSearch.domain.entity.Hospital;

@Repository
public interface HospitalRepository extends JpaRepository<Hospital, String> {
	
	@Query(nativeQuery = true, value = "select distinct sido_name from hospital order by sido_name")
	List<String> findAllSidoName();

	@Query(nativeQuery = true, value = "select distinct sggu_name from hospital where sido_name = :sidoName order by sggu_name")
	List<String> findAllGguName(String sidoName);

	Page<Hospital> findALLBySidoNameAndSgguNameOrderByName(String sidoName, String sgguName, Pageable pageable);
}
