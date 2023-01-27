package com.ktk.pcrSearch;

import static org.junit.jupiter.api.Assertions.*;

import java.net.URI;
import java.net.URISyntaxException;

import org.junit.jupiter.api.Test;
import org.springframework.web.util.UriComponentsBuilder;

class URIbuildTest {

	@Test
	void test() {
 		String url = "http://apis.data.go.kr/B551182/rprtPtntDiagCnfcInfoService1/getRprtPtntDiagCnfcInfo1?serviceKey=2397AZ16W0CRNwX58btT5QMtQ9gDRjo8TvCgF0Uj7QaSolpegysBotc5pVZg7HKyDBSK%2B%2BcabU%2FiMz5HfJmXVg%3D%3D&pageNo=1&_type=json";
 		
		try {
			URI uri = new URI(url);
			System.out.println(uri.toString());
			System.out.println(UriComponentsBuilder.fromHttpUrl(url).build().toUri());
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		
	}

}
