package com.example.exchanger.controller;

import java.util.List;

import com.example.exchanger.entity.CurrencyDto;
import com.example.exchanger.entity.InputForm;
import com.example.exchanger.service.JsonReaderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ExchangerController {
    @Autowired
    private JsonReaderService jsonReaderService;

    @GetMapping("/get")
    public String getJson(Model model) {
        List<CurrencyDto> currencies = jsonReaderService.readJSON();
        model.addAttribute("currencies", currencies);
        return "currencies";
    }

    @GetMapping ("/exchange")
    public String addExchangeForm(Model model) {
        InputForm inputForm = new InputForm();
        model.addAttribute("inputForm", inputForm);
        return "exchanger";
    }

    @PostMapping("/exchange")
    public String exchange(Model model, @ModelAttribute("inputForm") InputForm inputForm){
        Double result;
        List<CurrencyDto> currencyDtos = jsonReaderService.readJSON();
        for (CurrencyDto currencyDto: currencyDtos) {
            if (currencyDto.getCc().equals(inputForm.getShortName())) {
                result = inputForm.getValue() * currencyDto.getRate();
                model.addAttribute("result", result);
            }
        }
        return "exchanger";
    }
}
