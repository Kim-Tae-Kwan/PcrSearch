package com.ktk.pcrSearch.domain.dto;

import lombok.Data;

@Data
public class Body {
    public Items items;
    public long numOfRows;
    public long pageNo;
    public long totalCount;
}
