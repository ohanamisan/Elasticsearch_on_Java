package com.aoking.ElasticSearchOnJava.config;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;

public class ClientConfig {

    /**
     * RestHighLevelClient
     */
    public static RestHighLevelClient connectRest(){
        return new RestHighLevelClient(
                RestClient.builder(new HttpHost("localhost", 9200, "http")));
    }

}
