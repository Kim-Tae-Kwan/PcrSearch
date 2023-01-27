package com.ktk.pcrSearch.service;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ktk.pcrSearch.domain.entity.Hospital;
import com.ktk.pcrSearch.repository.HospitalRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class HospitalService {
	private final HospitalRepository hospitalRepository;
	
	public List<Hospital> findAll(Pageable pageable) {
		return hospitalRepository.findAll(pageable).getContent();
	}
	
	public List<String> findAllSidoName(){
		return hospitalRepository.findAllSidoName();
	}

	public List<String> findAllGguName(String sidoName) {
		return hospitalRepository.findAllGguName(sidoName);
	}

	public List<Hospital> findAllBySidoGuu(String sidoName, String gguName, Pageable pageable) {
		
		return hospitalRepository.findALLBySidoNameAndSgguName(sidoName, gguName, pageable).getContent();
	}
}
