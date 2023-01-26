package com.ktk.pcrSearch.domain.dto;

import lombok.Data;

@Data
public class Item {
    public String addr; //주소
    public String cv19ExmTyCd; // 코로나 검사유형코드
    public String dgmPrscPsblYn; //약제처방가능 여부
    public String diagBknPsblTelno; // 전화번호
    public String infcPtntDiagTyCd; //감염환자 진료유형코드
    public String onstpMadmYn;  //원스톱의료기관여부
    public String rprtPtntDiagPsblYn; //호흡기환자 진료가능 여부
    public String sgguNm; // 시군구명
    public String sidoNm; // 시도명
    public String yadmNm; // 요양기관명
    public String ykiho; //암호화된요양기호
}
