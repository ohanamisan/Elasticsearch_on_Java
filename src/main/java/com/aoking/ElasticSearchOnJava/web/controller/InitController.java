package com.aoking.ElasticSearchOnJava.web.controller;

import com.aoking.ElasticSearchOnJava.config.BulkConfig;
import com.aoking.ElasticSearchOnJava.config.ClientConfig;
import com.aoking.ElasticSearchOnJava.entity.Entry;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.elasticsearch.action.bulk.BulkProcessor;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class InitController {

    private final ObjectMapper jsonMapper = new ObjectMapper();
    private final String INDEX = "qiita";
    private final String TYPE  = "entry";

    /**
     * アプリ起動時にQiitaから記事を500件取得します
     */
    @RequestMapping("/init")
    public String initData(){
        try (RestHighLevelClient client = ClientConfig.connectRest();
             BulkProcessor bulk = BulkConfig.getBulk(client)){

            for (int i = 1; i <= 5; i++) {

                URL url = new URL("https://qiita.com/api/v2/items?page="+i+"&per_page=100");

                HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.setInstanceFollowRedirects(false);
                urlConnection.connect();

                BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                List<Entry> entryList = Arrays.asList(jsonMapper.readValue(reader.readLine(), Entry[].class));
                for (Entry entry : entryList) {
                    Map<String, Object> map = new HashMap<>();
                    map.put("id", entry.getId());
                    map.put("title", entry.getTitle());
                    map.put("body", entry.getBody());
                    map.put("url", entry.getUrl());
                    bulk.add(new IndexRequest(INDEX, TYPE, entry.getId()).source(map));
                }

                urlConnection.disconnect();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "succes";
    }

}
