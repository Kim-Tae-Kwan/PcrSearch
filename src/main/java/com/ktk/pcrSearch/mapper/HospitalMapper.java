package com.ktk.pcrSearch.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.ktk.pcrSearch.domain.dto.Item;
import com.ktk.pcrSearch.domain.entity.Hospital;

@Mapper(componentModel = "spring")
public abstract class HospitalMapper {
	
	@Mapping(target = "id", ignore = true)
	@Mapping(target = "addresss", source = "addr")
	@Mapping(target = "name", source = "yadmNm")
	@Mapping(target = "exmTypeCode", source = "cv19ExmTyCd")
	@Mapping(target = "prescriptionYn", source = "dgmPrscPsblYn")
	@Mapping(target = "tel", source = "diagBknPsblTelno")
	@Mapping(target = "inspectionYn", source = "rprtPtntDiagPsblYn")
	@Mapping(target = "sgguName", source = "sgguNm")
	@Mapping(target = "sidoName", source = "sidoNm")
	public abstract Hospital toEntity(Item item);
}
