package com.aoking.ElasticSearchOnJava.core.impl;

import com.aoking.ElasticSearchOnJava.config.ClientConfig;
import com.aoking.ElasticSearchOnJava.core.EntryCore;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * Elasticsearchからデータを取得します
 */

@Component
public class EntryCoreImpl implements EntryCore {

    private final String INDEX = "qiita";

    /**
     * RestHighLevelClientを使った検索
     */
    public Optional<SearchHits> fullTextSearch(String word){
        SearchHits hits = null;
        try(RestHighLevelClient client = ClientConfig.connectRest()){

            SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
            sourceBuilder.from(0);
            sourceBuilder.size(1000);
            sourceBuilder.query(QueryBuilders.boolQuery()
                    .should(QueryBuilders.matchPhraseQuery("title.full_text_search", word))
                    .should(QueryBuilders.matchPhraseQuery("body.full_text_search", word))
                    .minimumShouldMatch(1));

            SearchRequest req = new SearchRequest().indices(INDEX).source(sourceBuilder);

            SearchResponse res = client.search(req, RequestOptions.DEFAULT);
            hits = res.getHits();
        }catch (Exception e){
            e.printStackTrace();
        }
        return Optional.ofNullable(hits);
    }
}
