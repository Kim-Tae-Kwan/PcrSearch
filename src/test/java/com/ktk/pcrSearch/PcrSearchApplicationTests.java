package com.ktk.pcrSearch;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

import com.ktk.pcrSearch.domain.dto.ResponseDTO;
import com.ktk.pcrSearch.domain.entity.Hospital;
import com.ktk.pcrSearch.mapper.HospitalMapperImpl;
import com.ktk.pcrSearch.repository.HospitalRepository;

//@SpringBootTest
class PcrSearchApplicationTests {
	
	@Autowired
	HospitalRepository hospitalRepository;
	RestTemplate restTemplate = new RestTemplate();
	private final String url = "http://apis.data.go.kr/B551182/rprtPtntDiagCnfcInfoService1"
			+ "/getRprtPtntDiagCnfcInfo1"
			+ "?serviceKey=2397AZ16W0CRNwX58btT5QMtQ9gDRjo8TvCgF0Uj7QaSolpegysBotc5pVZg7HKyDBSK%2B%2BcabU%2FiMz5HfJmXVg%3D%3D"
			+ "&pageNo=1"
			+ "&_type=json";
	
	URI getUri(Long numOfRows){
		URI uri = null;
		
		try {
			uri = new URI(this.url + "&numOfRows=" + numOfRows);
		} catch (Exception e) {
		}
		
		return uri;
	}
	
	Long getTotalCount() {
		Long initCount = 2L;
		ResponseDTO dto = this.restTemplate.getForObject(getUri(initCount), ResponseDTO.class);
		return dto.getResponse().getBody().getTotalCount();
	}
	
	@Test
	void contextLoads() throws UnsupportedEncodingException, URISyntaxException {
		//1. RestTemplate으로 OpenAPI 들고오기
		RestTemplate template = new RestTemplate();
		
		// 전체 데이터 수 얻기
		Long totalCount = getTotalCount();
		// 전체 데이터 얻기
		HospitalMapperImpl mapper = new HospitalMapperImpl();
		ResponseDTO dto = template.getForObject(getUri(totalCount), ResponseDTO.class);
		
		// 
		List<Hospital> hospitals = dto.getItems().stream().map(mapper::toEntity).collect(Collectors.toList());
		hospitals.stream().forEach(hospitalRepository::save);
	}
}
