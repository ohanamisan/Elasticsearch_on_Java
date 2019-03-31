package com.aoking.ElasticSearchOnJava.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Tag {

    /**
     * タグ名
     */
    private String name;

}
