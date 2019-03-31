package com.aoking.ElasticSearchOnJava.web.facade;

import com.aoking.ElasticSearchOnJava.web.dto.EntryDto;
import com.aoking.ElasticSearchOnJava.web.form.EntrySearchForm;

import java.util.List;

public interface EntryFacade {

    /**
     * 全文検索
     */
    List<EntryDto> fullTextSearch(EntrySearchForm form);

    /**
     * 全文検索の結果件数
     */
    long fullTextSearchCount(EntrySearchForm form);
}
