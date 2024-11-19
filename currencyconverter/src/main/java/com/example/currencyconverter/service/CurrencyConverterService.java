package com.example.currencyconverter.service;


import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


@Service
public class CurrencyConverterService {
    private static final String API_URL = "https://api.exchangerate-api.com/v4/latest/";
    private final List<ConversionHistory> recentConversions = Collections.synchronizedList(new ArrayList<>());
    private static final int MAX_HISTORY_SIZE = 10;
    
    public double convert(double amount, String fromCurrency, String toCurrency) {
        RestTemplate restTemplate = new RestTemplate();
        String url = API_URL + fromCurrency;
        
        try {
            ExchangeRateResponse response = restTemplate.getForObject(url, ExchangeRateResponse.class);
            
            if (response != null && response.getRates().containsKey(toCurrency)) {
                double rate = response.getRates().get(toCurrency);
                double result = amount * rate;
                
                // Add to history using BigDecimal for precision
                addToHistory(
                    BigDecimal.valueOf(amount),
                    fromCurrency,
                    toCurrency,
                    BigDecimal.valueOf(rate),
                    BigDecimal.valueOf(result)
                );
                
                return result;
            } else {
                throw new IllegalArgumentException("Invalid target currency");
            }
        } catch (Exception e) {
            throw new RuntimeException("Error fetching exchange rates: " + e.getMessage(), e);
        }
    }
    
    private void addToHistory(BigDecimal amount, String fromCurrency, String toCurrency,
                            BigDecimal rate, BigDecimal result) {
        ConversionHistory conversion = new ConversionHistory(
            amount,
            fromCurrency,
            toCurrency,
            rate,
            result,
            LocalDateTime.now()
        );
        
        recentConversions.add(0, conversion);
        
        if (recentConversions.size() > MAX_HISTORY_SIZE) {
            recentConversions.remove(recentConversions.size() - 1);
        }
    }
    
    public List<ConversionHistory> getRecentConversions() {
        return Collections.unmodifiableList(recentConversions);
    }
    
    public void clearHistory() {
        recentConversions.clear();
    }
}


