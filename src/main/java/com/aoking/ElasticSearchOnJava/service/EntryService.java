package com.aoking.ElasticSearchOnJava.service;

import com.aoking.ElasticSearchOnJava.core.EntryDao;
import com.aoking.ElasticSearchOnJava.entity.Entry;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.elasticsearch.search.SearchHit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EntryService {

    @Autowired
    EntryDao dao;

    private final ObjectMapper jsonMapper = new ObjectMapper();

    /**
     * 全文検索
     */
    public List<Entry> fullTextSearch(String word){
        List<Entry> list = new ArrayList<Entry>();
        dao.fullTextSearch(word).ifPresent(hits -> {
           for(SearchHit hit: hits.getHits()) {
               list.add(extractValue(hit));
           }
        });
        return list;
    }

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

}