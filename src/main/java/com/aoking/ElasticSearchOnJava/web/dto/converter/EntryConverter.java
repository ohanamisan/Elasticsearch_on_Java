package com.aoking.ElasticSearchOnJava.web.dto.converter;

import com.aoking.ElasticSearchOnJava.entity.Entry;
import com.aoking.ElasticSearchOnJava.web.dto.EntryDto;

public class EntryConverter {

    public static EntryDto convert(Entry entry){
        return EntryDto.builder()
                       .title(entry.getTitle())
                       .url(entry.getUrl())
                       .build();
    }

}
