package com.ktk.pcrSearch.domain.dto.kakaoLocal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Document {
    public Object address;
    public String addressName;
    public String addressType;
    public RoadAddress roadAddress;
    public String x;
    public String y;
}
