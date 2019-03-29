package com.aoking.ElasticSearchOnJava.web.facade;

import com.aoking.ElasticSearchOnJava.web.dto.EntryDto;

import java.util.List;

public interface EntryFacade {

    /**
     * 全文検索
     */
    List<EntryDto> fullTextSearch(String word);

}
