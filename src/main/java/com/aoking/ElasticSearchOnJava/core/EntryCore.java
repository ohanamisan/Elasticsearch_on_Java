package com.aoking.ElasticSearchOnJava.core;

import org.elasticsearch.search.SearchHits;

import java.util.Optional;

public interface EntryCore {

    /**
     * RestHighLevelClientを使った検索
     */
    public Optional<SearchHits> fullTextSearch(String word);

}
