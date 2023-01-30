package com.ktk.pcrSearch;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.ktk.pcrSearch.domain.dto.kakaoLocal.KakaoLocalResponse;

class KakaoLocalTest {

	@Test
	void test() {
		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", "KakaoAK b43630b1ce6a95f2dc28cfef5a3aa9fb");
		HttpEntity<HttpHeaders> request = new HttpEntity<>(headers);
		
		RestTemplate template = new RestTemplate();
		
		ResponseEntity<KakaoLocalResponse> response = template.exchange("https://dapi.kakao.com/v2/local/search/address.json?query=범내남로 38", HttpMethod.GET, request, KakaoLocalResponse.class);
		
	}
}
