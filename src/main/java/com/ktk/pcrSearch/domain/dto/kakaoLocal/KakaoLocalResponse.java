package com.ktk.pcrSearch.domain.dto.kakaoLocal;

import java.util.List;

import lombok.Data;

@Data
public class KakaoLocalResponse {
    public List<Document> documents;
    public Meta meta;
}
