package com.ktk.pcrSearch.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ktk.pcrSearch.domain.entity.Hospital;
import com.ktk.pcrSearch.service.HospitalService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/hospital")
public class HospitalController {
	
	private final HospitalService hospitalService;
	
	@GetMapping()
	public ResponseEntity<List<Hospital>> findAll(@RequestParam(required = false) String sido, @RequestParam(required = false) String ggu,  Pageable pageable){
		System.out.println(sido);
		return ResponseEntity.ok(hospitalService.findAll(pageable)); 
	}
	
	@GetMapping("/{sidoName}/{gguName}")
	public ResponseEntity<List<Hospital>> findAllBySidoGuu(@PathVariable String sidoName, @PathVariable String gguName,  Pageable pageable){
		return ResponseEntity.ok(hospitalService.findAllBySidoGuu(sidoName, gguName, pageable)); 
	}
	
	@GetMapping("/sidoName")
	public ResponseEntity<List<String>> findAllSidoName(){
		return ResponseEntity.ok(hospitalService.findAllSidoName()); 
	}
	
	@GetMapping("/{sidoName}/gguName")
	public ResponseEntity<List<String>> findAllGguName(@PathVariable String sidoName){
		return ResponseEntity.ok(hospitalService.findAllGguName(sidoName)); 
	}
}
