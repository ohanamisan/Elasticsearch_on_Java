package com.aoking.ElasticSearchOnJava.web.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class EntryDto {

    private String title;

    private String url;

}
