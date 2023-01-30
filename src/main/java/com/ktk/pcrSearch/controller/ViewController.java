package com.ktk.pcrSearch.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.ktk.pcrSearch.service.HospitalService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class ViewController {
	
	private final HospitalService hospitalService;
	
	@GetMapping("/")
	public String index(Model model) {
		
		List<String> sidoNames = hospitalService.findAllSidoName();
		List<String> gguNames = hospitalService.findAllGguName(sidoNames.get(0));
		
		model.addAttribute("sidoNames", sidoNames);
		model.addAttribute("gguNames", gguNames);
		return "index";
	}
	
	@GetMapping("/map")
	public String map(Model model) {
		return "map";
	}
}
