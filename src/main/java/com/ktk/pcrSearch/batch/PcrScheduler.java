package com.ktk.pcrSearch.batch;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.boot.CommandLineRunner;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.ktk.pcrSearch.domain.dto.ResponseDTO;
import com.ktk.pcrSearch.domain.entity.Hospital;
import com.ktk.pcrSearch.mapper.HospitalMapperImpl;
import com.ktk.pcrSearch.repository.HospitalRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class PcrScheduler implements CommandLineRunner{
	
	private final HospitalMapperImpl mapper;
	private final HospitalRepository hospitalRepository;
	private RestTemplate restTemplate = new RestTemplate();
	private final String url = "http://apis.data.go.kr/B551182/rprtPtntDiagCnfcInfoService1"
			+ "/getRprtPtntDiagCnfcInfo1"
			+ "?serviceKey=2397AZ16W0CRNwX58btT5QMtQ9gDRjo8TvCgF0Uj7QaSolpegysBotc5pVZg7HKyDBSK%2B%2BcabU%2FiMz5HfJmXVg%3D%3D"
			+ "&pageNo=1"
			+ "&_type=json";
	
	@Override
	public void run(String... args) throws Exception {
		
		// 초기 데이터 입력
		if(hospitalRepository.count() <= 0) {
			log.info("Open API Data init...");
			hospitalRepository.saveAll(getHospitals());
		}
	}
	
	@Transactional
	@Scheduled(cron = "0 0 9 * * *")
	public void updateHospitalInfo() {
		
		log.info("Open API data download...");
		List<Hospital> hospitals = getHospitals();
		
		log.info("Remove all data");
		hospitalRepository.deleteAll();
		
		log.info("Save all data");
		hospitalRepository.saveAll(hospitals);
		
		log.info("Hospital information update success!");
	}
	
	private List<Hospital> getHospitals() {
		Long totalCount = getTotalCount();
		ResponseDTO dto = restTemplate.getForObject(getUri(totalCount), ResponseDTO.class);
		
		return dto.getItems().stream().map(mapper::toEntity).collect(Collectors.toList());
	}
	
	private Long getTotalCount() {
		Long initCount = 2L;
		ResponseDTO dto = this.restTemplate.getForObject(getUri(initCount), ResponseDTO.class);
		return dto.getResponse().getBody().getTotalCount();
	}
	
	private URI getUri(Long numOfRows){
		URI uri = null;
		
		try {
			uri = new URI(this.url + "&numOfRows=" + numOfRows);
		} catch (Exception e) {
		}
		
		return uri;
	}
}
