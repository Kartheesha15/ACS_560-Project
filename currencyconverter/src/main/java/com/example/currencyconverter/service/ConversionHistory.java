package com.example.currencyconverter.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;


public class ConversionHistory {
    private final BigDecimal amount;
    private final String fromCurrency;
    private final String toCurrency;
    private final BigDecimal rate;
    private final BigDecimal result;
    private final LocalDateTime timestamp;
    
    public ConversionHistory(BigDecimal amount, String fromCurrency, String toCurrency,
                           BigDecimal rate, BigDecimal result, LocalDateTime timestamp) {
        this.amount = amount;
        this.fromCurrency = fromCurrency;
        this.toCurrency = toCurrency;
        this.rate = rate;
        this.result = result;
        this.timestamp = timestamp;
    }
    
    // Updated getters to return BigDecimal instead of double
    public BigDecimal getAmount() { return amount; }
    public String getFromCurrency() { return fromCurrency; }
    public String getToCurrency() { return toCurrency; }
    public BigDecimal getRate() { return rate; }
    public BigDecimal getResult() { return result; }
    public LocalDateTime getTimestamp() { return timestamp; }

    @Override
    public String toString() {
        return String.format(
            "%s %s = %s %s (rate: %f, timestamp: %s)",
            amount, fromCurrency, result, toCurrency, rate, timestamp
        );
    }
}
