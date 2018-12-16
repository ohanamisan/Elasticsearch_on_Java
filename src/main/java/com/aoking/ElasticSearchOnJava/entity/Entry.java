package com.aoking.ElasticSearchOnJava.entity;

import lombok.Builder;
import lombok.Getter;

/**
 * Qiitaの記事のエントリー
 */

@Builder
@Getter
public class Entry {

    /**
     * 記事の一意なID
     */
    private String id;

    /**
     * 投稿ユーザ名
     */
    private String userName;

    /**
     * 記事のタイトル
     */
    private String title;

    /**
     * 記事の内容
     */
    private String content;

    /**
     * 記事のURL
     */
    private String url;

}
