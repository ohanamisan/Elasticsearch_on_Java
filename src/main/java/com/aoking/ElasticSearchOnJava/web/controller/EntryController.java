package com.aoking.ElasticSearchOnJava.web.controller;

import com.aoking.ElasticSearchOnJava.web.dto.EntryDto;
import com.aoking.ElasticSearchOnJava.web.facade.EntryFacade;
import com.aoking.ElasticSearchOnJava.web.form.EntrySearchForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class EntryController {

    @Autowired
    EntryFacade facade;

    @ModelAttribute
    public EntrySearchForm setUpForm(){
        return new EntrySearchForm();
    }

    @RequestMapping(value = "/")
    public String index() {
        return "home";
    }

    @RequestMapping(value = "/search")
    public String search(Model model, @Validated EntrySearchForm form, BindingResult rs) {
        if(rs.hasErrors()){
            return "home";
        }
        List<EntryDto> list = facade.fullTextSearch(form);
        model.addAttribute("entries", list);
        model.addAttribute("count", facade.fullTextSearchCount(form));
        return "home";
    }

}
