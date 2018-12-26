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

public class BulkConfig {

    /**
     * BulkAPIの基本設定を取得します
     */
    public static BulkProcessor getBulk(RestHighLevelClient client){
        return BulkProcessor.builder((request, bulkListener) ->
                client.bulkAsync(request, RequestOptions.DEFAULT, bulkListener), new BulkProcessor.Listener() {

            @Override
            public void beforeBulk(long l, BulkRequest bulkRequest) {
                System.out.println("bulkRequest = " + bulkRequest.numberOfActions());
            }

            @Override
            public void afterBulk(long l, BulkRequest bulkRequest, Throwable throwable) {
                throwable.printStackTrace();
            }

            @Override
            public void afterBulk(long l, BulkRequest bulkRequest, BulkResponse bulkResponse) {
                System.out.println(
                        "bulkResponse = " + bulkResponse.hasFailures() + " " + bulkResponse.buildFailureMessage());
            }
        }).setBulkActions(20)
          .setBulkSize(new ByteSizeValue(5, ByteSizeUnit.MB))
          .setFlushInterval(TimeValue.timeValueSeconds(5))
          .setConcurrentRequests(0)
          .setBackoffPolicy(BackoffPolicy.exponentialBackoff(TimeValue.timeValueMillis(100), 3))
          .build();
    }
}
