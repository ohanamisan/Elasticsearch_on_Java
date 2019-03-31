package com.aoking.ElasticSearchOnJava.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

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

    /**
     * 記事のタグ
     */
    private List<Tag> tags;

    /**
     * tagsのname一覧を取得します
     */
    public List<String> getTagNames(){
        return tags.stream().map(Tag::getName).collect(Collectors.toList());
    }

    @Override
    public String toString(){
        return  "id: "    + this.id + "\n" +
                "title: " + this.title + "\n" +
                "body: "  + this.body.substring(0,20) + "..." + "\n" +
                "url: "   + this.url;
    }
}
