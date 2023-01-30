package com.ktk.pcrSearch.batch;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.ktk.pcrSearch.domain.dto.Item;
import com.ktk.pcrSearch.domain.dto.ResponseDTO;
import com.ktk.pcrSearch.domain.dto.kakaoLocal.Document;
import com.ktk.pcrSearch.domain.dto.kakaoLocal.KakaoLocalResponse;
import com.ktk.pcrSearch.domain.entity.Hospital;
import com.ktk.pcrSearch.mapper.HospitalMapperImpl;
import com.ktk.pcrSearch.repository.HospitalRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class PcrScheduler implements CommandLineRunner{
	
	@Value("${kakaoKey}")
	private String kakaoApiKey;
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
			List<Hospital> hospitals = getHospitals();
			hospitalRepository.saveAll(hospitals);
			log.info("Open API Data init success!!");
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
		
		// openAPI 총 갯수 구하기
		Long totalCount = getTotalCount();
		
		// openAPI 전체 데이터 들고오기
		List<Item> items = restTemplate.getForObject(getUri(totalCount), ResponseDTO.class).getItems();
		log.info("Download data count - " + items.size());
		
		// openAPI -> Hospital 엔티티 변환
		List<Hospital> hospitals = new ArrayList<>();
		for(int i = 0; i < items.size(); i++ ) {
			Item item = items.get(i);
			String address = item.getAddr().split(",")[0];
			
			// 주소로 좌표 구하기.
			Document localInfo = getLocalInfo(address);
			
			Hospital hospital = mapper.toEntity(item, localInfo);
			log.info(String.format("Hostitpal data (%d/%d)", i, items.size()));
			hospitals.add(hospital);
		}
		
		log.info("Get hospital data success!");
		return hospitals;
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
	
	private Document getLocalInfo(String address) {
		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", "KakaoAK " + kakaoApiKey);
		HttpEntity<HttpHeaders> request = new HttpEntity<>(headers);
		
		KakaoLocalResponse res = restTemplate.exchange("https://dapi.kakao.com/v2/local/search/address.json?query=" + address, HttpMethod.GET, request, KakaoLocalResponse.class).getBody();
		Integer count = res.getDocuments().size();
		
		if(count <= 0) {
			return Document.builder()
					.x("0")
					.y("0")
					.build();
		}else {
			return res.getDocuments().get(0);
		}
	}
}
