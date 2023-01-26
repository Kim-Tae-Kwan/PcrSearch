package com.ktk.pcrSearch.domain.dto;

import java.util.List;

import lombok.Data;

@Data
public class ResponseDTO {
    public Response response;
    
    public List<Item> getItems() {
    	return response.getBody().getItems().getItem();
    }
}
