package com.aoking.ElasticSearchOnJava.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

/**
 * Qiitaの記事のエントリー
 */

@Data
@JsonIgnoreProperties(ignoreUnknown=true)
public class Entry {

    /**
     * 記事の一意なID
     */
    private String id;

    /**
     * 記事のタイトル
     */
    private String title;

    /**
     * 記事の内容
     */
    private String body;

    /**
     * 記事のURL
     */
    private String url;

}
