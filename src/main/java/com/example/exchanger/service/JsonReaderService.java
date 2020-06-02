package com.example.exchanger.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.example.exchanger.entity.CurrencyDto;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;

@Service
public class JsonReaderService {
    public static final String EXCHANGE_URL = "https://bank.gov.ua/NBUStatService/v1/statdirectory/exchange?json";

    public List<CurrencyDto> readJSON() {
        String result = "";
        List<CurrencyDto> currencyDtos;
        HttpGet request = new HttpGet(EXCHANGE_URL);

        try (CloseableHttpClient httpClient = HttpClients.createDefault();
             CloseableHttpResponse response = httpClient.execute(request)) {

            HttpEntity entity = response.getEntity();
            if (entity != null) {
                ObjectMapper objectMapper = new ObjectMapper();
                result = EntityUtils.toString(entity);
                currencyDtos = objectMapper.readValue(result, new TypeReference<List<CurrencyDto>>(){});
                return currencyDtos;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }
}
