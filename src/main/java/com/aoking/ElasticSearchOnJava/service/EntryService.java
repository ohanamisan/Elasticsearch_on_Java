package com.aoking.ElasticSearchOnJava.service;

import com.aoking.ElasticSearchOnJava.entity.Entry;

import java.util.List;

public interface EntryService {

    /**
     * 全文検索
     */
    List<Entry> fullTextSearch(String word, int display);

    /**
     * 全文検索の結果件数
     */
    long fullTextSearchCount(String word, int display);

}
