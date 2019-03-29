package com.aoking.ElasticSearchOnJava.service.impl;

import com.aoking.ElasticSearchOnJava.core.EntryCore;
import com.aoking.ElasticSearchOnJava.entity.Entry;
import com.aoking.ElasticSearchOnJava.service.EntryService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.elasticsearch.search.SearchHit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EntryServiceImpl implements EntryService {

    @Autowired
    EntryCore entryCore;

    private final ObjectMapper jsonMapper = new ObjectMapper();

    /**
     * SearchHitをEntryにコンバートする処理
     */
    private Entry extractValue(SearchHit hit){
        Entry entry = null;
        try{
            entry = jsonMapper.readValue(hit.getSourceAsString(), Entry.class);
        }catch (Exception e){
            e.printStackTrace();
        }
        return entry;
    }

    /**
     * 全文検索
     */
    public List<Entry> fullTextSearch(String word){
        List<Entry> list = new ArrayList<Entry>();
        entryCore.fullTextSearch(word).ifPresent(hits -> {
           for(SearchHit hit: hits.getHits()) {
               list.add(extractValue(hit));
           }
        });
        return list;
    }


}