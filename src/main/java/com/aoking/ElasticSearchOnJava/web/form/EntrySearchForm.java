package com.aoking.ElasticSearchOnJava.web.form;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class EntrySearchForm {

    @NotBlank(message="検索条件は必須です")
    private String word;

    @Max(50)
    @Min(10)
    @NotNull(message="取得件数が指定されていません")
    private Integer display;

}
