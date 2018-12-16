package com.aoking.ElasticSearchOnJava;

import com.aoking.ElasticSearchOnJava.config.BulkConfig;
import com.aoking.ElasticSearchOnJava.config.ClientConfig;
import com.aoking.ElasticSearchOnJava.entity.Entry;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.elasticsearch.action.bulk.BulkProcessor;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootApplication
public class ElasticSearchOnJavaApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext ctx = SpringApplication.run(ElasticSearchOnJavaApplication.class, args);
		ElasticSearchOnJavaApplication app = ctx.getBean(ElasticSearchOnJavaApplication.class);
		app.initData();
	}

	private final ObjectMapper jsonMapper = new ObjectMapper();
	private final String INDEX = "qiita";
	private final String TYPE  = "entry";

	/**
	 * アプリ起動時にQiitaから記事を500件取得します
	 */
	public void initData(){
		try (RestHighLevelClient client = ClientConfig.connectRest();
			 BulkProcessor bulk = BulkConfig.getBulk(client)){

			for (int i = 1; i <= 5; i++) {

				URL url = new URL("https://qiita.com/api/v2/items?page="+i+"&per_page=100");

				HttpsURLConnection urlconn = (HttpsURLConnection) url.openConnection();
				urlconn.setRequestMethod("GET");
				urlconn.setInstanceFollowRedirects(false);
				urlconn.connect();

				BufferedReader reader = new BufferedReader(new InputStreamReader(urlconn.getInputStream()));
				List<Entry> entryList = Arrays.asList(jsonMapper.readValue(reader.readLine(), Entry[].class));
				for (Entry entry : entryList) {
					Map<String, Object> map = new HashMap<>();
					map.put("id", entry.getId());
					map.put("title", entry.getTitle());
					map.put("body", entry.getBody());
					map.put("url", entry.getUrl());
					bulk.add(new IndexRequest(INDEX, TYPE, entry.getId()).source(map));
				}

				urlconn.disconnect();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
