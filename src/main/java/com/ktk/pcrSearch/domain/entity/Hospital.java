package com.ktk.pcrSearch.domain.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class Hospital {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private String id;
	private String address; //주소
	private String name; // 요양기관명
	private String exmTypeCode; // 코로나 검사유형코드
	private String prescriptionYn; //약제처방가능 여부
	private String tel; // 전화번호
	private String inspectionYn; //호흡기환자 진료가능 여부
	private String sgguName; // 시군구명
	private String sidoName; // 시도명
	private String x; // 경도 (longitude)
	private String y; // 위도 (latitude)
}
