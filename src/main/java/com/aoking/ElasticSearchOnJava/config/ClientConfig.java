package com.aoking.ElasticSearchOnJava.config;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.net.InetAddress;
import java.net.UnknownHostException;

@Component
public class ClientConfig {

    /**
     * TransportClient
     */
    public static TransportClient transportClient;

    @PostConstruct
    public void connectTransport(){
        TransportClient client = new PreBuiltTransportClient(Settings.EMPTY);
        try{
            client.addTransportAddress(new TransportAddress(InetAddress.getByName("localhost"),9300));
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        transportClient = client;
    }

    @PreDestroy
    public void closeTransport(){
        transportClient.close();
    }

    /**
     * RestHighLevelClient
     */
    public static RestHighLevelClient connectRest(){
        return new RestHighLevelClient(
                RestClient.builder(new HttpHost("localhost", 9200, "http")));
    }

}
