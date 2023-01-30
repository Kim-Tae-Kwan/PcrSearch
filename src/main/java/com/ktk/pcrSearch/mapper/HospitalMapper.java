package com.ktk.pcrSearch.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.ktk.pcrSearch.domain.dto.Item;
import com.ktk.pcrSearch.domain.dto.kakaoLocal.Document;
import com.ktk.pcrSearch.domain.dto.kakaoLocal.KakaoLocalResponse;
import com.ktk.pcrSearch.domain.entity.Hospital;

@Mapper(componentModel = "spring")
public abstract class HospitalMapper {
	
	@Mapping(target = "id", ignore = true)
	@Mapping(target = "address", source = "item.addr")
	@Mapping(target = "name", source = "item.yadmNm")
	@Mapping(target = "exmTypeCode", source = "item.cv19ExmTyCd")
	@Mapping(target = "prescriptionYn", source = "item.dgmPrscPsblYn")
	@Mapping(target = "tel", source = "item.diagBknPsblTelno")
	@Mapping(target = "inspectionYn", source = "item.rprtPtntDiagPsblYn")
	@Mapping(target = "sgguName", source = "item.sgguNm")
	@Mapping(target = "sidoName", source = "item.sidoNm")
	@Mapping(target = "x", source = "localInfo.x")
	@Mapping(target = "y", source = "localInfo.y")
	public abstract Hospital toEntity(Item item, Document localInfo);
}
