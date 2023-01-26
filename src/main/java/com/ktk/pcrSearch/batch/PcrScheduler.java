package com.ktk.pcrSearch.batch;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.ktk.pcrSearch.domain.dto.ResponseDTO;
import com.ktk.pcrSearch.domain.entity.Hospital;
import com.ktk.pcrSearch.mapper.HospitalMapperImpl;
import com.ktk.pcrSearch.repository.HospitalRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class PcrScheduler {
	
	private final HospitalMapperImpl mapper;
	private final HospitalRepository hospitalRepository;
	private RestTemplate restTemplate = new RestTemplate();
	private final String url = "http://apis.data.go.kr/B551182/rprtPtntDiagCnfcInfoService1"
			+ "/getRprtPtntDiagCnfcInfo1"
			+ "?serviceKey=2397AZ16W0CRNwX58btT5QMtQ9gDRjo8TvCgF0Uj7QaSolpegysBotc5pVZg7HKyDBSK%2B%2BcabU%2FiMz5HfJmXVg%3D%3D"
			+ "&pageNo=1"
			+ "&_type=json";
	
	@Transactional
	@Scheduled(cron = "* * 9 * * *")
	public void updateHospitalInfo() {
		// 전체 데이터 수 얻기
		Long totalCount = getTotalCount();
		
		// 전체 데이터 얻기
		ResponseDTO dto = restTemplate.getForObject(getUri(totalCount), ResponseDTO.class);
		
		// Entity 변환
		List<Hospital> hospitals = dto.getItems().stream().map(mapper::toEntity).collect(Collectors.toList());
		
		// 전체 데이터 삭제
		hospitalRepository.deleteAll();
		
		// 전체 데이터 저장
		hospitals.stream().forEach(hospitalRepository::save);
	}
	
	private URI getUri(Long numOfRows){
		URI uri = null;
		
		try {
			uri = new URI(this.url + "&numOfRows=" + numOfRows);
		} catch (Exception e) {
		}
		
		return uri;
	}
	
	private Long getTotalCount() {
		Long initCount = 2L;
		ResponseDTO dto = this.restTemplate.getForObject(getUri(initCount), ResponseDTO.class);
		return dto.getResponse().getBody().getTotalCount();
	}
}
