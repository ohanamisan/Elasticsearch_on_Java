package com.aoking.ElasticSearchOnJava.web.controller;

import com.aoking.ElasticSearchOnJava.service.EntryService;
import com.aoking.ElasticSearchOnJava.web.converter.EntryConverter;
import com.aoking.ElasticSearchOnJava.web.dto.EntryDto;
import com.aoking.ElasticSearchOnJava.web.form.EntrySearchForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class EntryController {

    @Autowired
    EntryService service;

    @ModelAttribute
    public EntrySearchForm setUpForm(){
        return new EntrySearchForm();
    }

    @RequestMapping(value = "/")
    public String index() {
        return "home";
    }

    @RequestMapping(value = "/search")
    public String search(Model model, @Validated EntrySearchForm form,  BindingResult rs) {
        if(rs.hasErrors()){
            return "home";
        }
        List<EntryDto> list = service.fullTextSearch(form.getWord())
                                     .stream()
                                     .map(entry -> EntryConverter.convert(entry))
                                     .collect(Collectors.toList());
        model.addAttribute("entries", list);
        return "home";
    }

}
