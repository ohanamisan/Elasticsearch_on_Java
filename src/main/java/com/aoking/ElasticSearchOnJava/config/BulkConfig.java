package com.aoking.ElasticSearchOnJava.config;

import com.aoking.ElasticSearchOnJava.entity.Entry;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.elasticsearch.action.bulk.BackoffPolicy;
import org.elasticsearch.action.bulk.BulkProcessor;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.ByteSizeUnit;
import org.elasticsearch.common.unit.ByteSizeValue;
import org.elasticsearch.common.unit.TimeValue;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class BulkConfig {

    private final ObjectMapper jsonMapper = new ObjectMapper();
    private final String INDEX = "qiita";
    private final String TYPE  = "entry";

    private BulkProcessor getBulk(){
        RestHighLevelClient client = ClientConfig.connectRest();
        BulkProcessor.Listener listener = new BulkProcessor.Listener() {
            @Override
            public void beforeBulk(long executionId, BulkRequest request) {
                System.out.println("bulkRequest = " + request.numberOfActions());
            }

            @Override
            public void afterBulk(long executionId, BulkRequest request,
                                  BulkResponse response) {
                System.out.println("bulkResponse = " + response.hasFailures() + " " + response.buildFailureMessage());
            }

            @Override
            public void afterBulk(long executionId, BulkRequest request,
                                  Throwable failure) {
                failure.printStackTrace();
            }
        };

        return BulkProcessor.builder(
                (request, bulkListener) ->
                        client.bulkAsync(request, RequestOptions.DEFAULT, bulkListener), listener)
                            .setBulkActions(20)
                            .setBulkSize(new ByteSizeValue(5, ByteSizeUnit.MB))
                            .setFlushInterval(TimeValue.timeValueSeconds(5)).setConcurrentRequests(0)
                            .setBackoffPolicy(BackoffPolicy.exponentialBackoff(TimeValue.timeValueMillis(100), 3))
                            .build();
    }

    @PostConstruct
    public void initData() {
        try {
            URL url = new URL("https://qiita.com/api/v2/items?page=2&per_page=100");

            HttpsURLConnection urlconn = (HttpsURLConnection) url.openConnection();
            urlconn.setRequestMethod("GET");
            urlconn.setInstanceFollowRedirects(false);
            urlconn.connect();
            BufferedReader reader = new BufferedReader(new InputStreamReader(urlconn.getInputStream()));

            List<Entry> entryList = Arrays.asList(jsonMapper.readValue(reader.readLine(), Entry[].class));
            int count = 0;
            for (Entry entry : entryList) {
                Map<String, Entry> map = new HashMap<>();
                map.put("entry", entry);
                getBulk().add(new IndexRequest(INDEX, TYPE, entry.getId()).source(map));

            }
            urlconn.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
