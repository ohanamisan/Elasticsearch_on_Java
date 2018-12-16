package com.aoking.ElasticSearchOnJava.dao;

import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.search.SearchHits;
import org.springframework.stereotype.Component;

/**
 * Elasticsearchからデータを取得します
 */

@Component
public class EntryDao {

    /**
     * TransportClientを使った検索
     */
    public SearchHits fullTextSearch(TransportClient client, String word){
        return null;
    }

    /**
     * RestHighLevelClientを使った検索
     */
    public SearchHits fullTextSearch(RestHighLevelClient client, String word){
        return null;
    }


}
