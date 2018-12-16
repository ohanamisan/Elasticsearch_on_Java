package com.aoking.ElasticSearchOnJava.service;

import com.aoking.ElasticSearchOnJava.dao.EntryDao;
import com.aoking.ElasticSearchOnJava.entity.Entry;
import org.elasticsearch.search.SearchHits;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SearchService {

    @Autowired
    EntryDao dao;

    /**
     * 全文検索
     */
    public List<Entry> fullTextSearch(String word){
        List<Entry> list = new ArrayList<Entry>();
        list.add(Entry.builder().id("aaa").title("test").url("https://github.com/abraunegg/onedrive").build());
        list.add(Entry.builder().id("bbb").title("test2").url("https://github.com/abraunegg/onedrive").build());
        return list;
    }

    /**
     * TODO:SearchHitsをEntryのListにコンバートする処理
     */
    private List<Entry> extractHits(SearchHits hits){
        List<Entry> list = new ArrayList<Entry>();
        return list;
    }

}