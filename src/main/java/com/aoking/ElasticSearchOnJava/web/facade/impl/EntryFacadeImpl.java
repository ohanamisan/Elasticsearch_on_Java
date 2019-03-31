package com.aoking.ElasticSearchOnJava.web.facade.impl;

import com.aoking.ElasticSearchOnJava.entity.Entry;
import com.aoking.ElasticSearchOnJava.service.EntryService;
import com.aoking.ElasticSearchOnJava.web.dto.converter.EntryConverter;
import com.aoking.ElasticSearchOnJava.web.dto.EntryDto;
import com.aoking.ElasticSearchOnJava.web.facade.EntryFacade;
import com.aoking.ElasticSearchOnJava.web.form.EntrySearchForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class EntryFacadeImpl implements EntryFacade {

    @Autowired
    EntryService entryService;

    @Override
    public List<EntryDto> fullTextSearch(EntrySearchForm form) {

        List<Entry> entryList = entryService.fullTextSearch(form.getWord(), form.getDisplay());

        List<EntryDto> dtoList = entryList.stream().map(entry -> EntryConverter.convert(entry)).collect(Collectors.toList());
        return dtoList;
    }

    @Override
    public long fullTextSearchCount(EntrySearchForm form) {
        return entryService.fullTextSearchCount(form.getWord(), form.getDisplay());
    }


}
