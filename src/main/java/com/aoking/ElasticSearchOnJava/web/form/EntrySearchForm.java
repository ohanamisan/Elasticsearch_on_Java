package com.aoking.ElasticSearchOnJava.web.form;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class EntrySearchForm {

    @NotBlank(message="検索条件は必須です")
    private String word;

}
