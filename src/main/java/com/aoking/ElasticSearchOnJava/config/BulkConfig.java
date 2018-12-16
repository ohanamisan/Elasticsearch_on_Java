package com.aoking.ElasticSearchOnJava.config;

import org.elasticsearch.action.bulk.BackoffPolicy;
import org.elasticsearch.action.bulk.BulkProcessor;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.ByteSizeUnit;
import org.elasticsearch.common.unit.ByteSizeValue;
import org.elasticsearch.common.unit.TimeValue;
import org.springframework.stereotype.Component;

@Component
public class BulkConfig {

    /**
     * BulkAPIの基本設定を取得します
     */
    public static BulkProcessor getBulk(RestHighLevelClient client){
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

}
