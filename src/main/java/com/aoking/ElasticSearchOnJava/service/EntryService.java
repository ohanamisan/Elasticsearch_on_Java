package com.aoking.ElasticSearchOnJava.service;

import com.aoking.ElasticSearchOnJava.entity.Entry;

import java.util.List;

public interface EntryService {

    /**
     * 全文検索
     */
    public List<Entry> fullTextSearch(String word);

}
