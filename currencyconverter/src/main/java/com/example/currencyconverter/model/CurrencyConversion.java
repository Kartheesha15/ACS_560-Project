package com.example.currencyconverter.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class CurrencyConversion {
    public BigDecimal amount;
    public String fromCurrency;
    public String toCurrency;
    public BigDecimal convertedAmount;
    public LocalDateTime timestamp;
    public BigDecimal rate;

    // Default constructor
    public CurrencyConversion() {
        this.timestamp = LocalDateTime.now();
    }
    public CurrencyConversion(BigDecimal amount, String fromCurrency, String toCurrency) {
        this();
        this.amount = amount;
        this.fromCurrency = fromCurrency;
        this.toCurrency = toCurrency;
    }

    // Getters and Setters
    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        if (amount == null) {
            throw new IllegalArgumentException("Amount cannot be null");
        }
        this.amount = amount;
    }

    public String getFromCurrency() {
        return fromCurrency;
    }

    public void setFromCurrency(String fromCurrency) {
        if (fromCurrency == null || fromCurrency.trim().isEmpty()) {
            throw new IllegalArgumentException("From currency cannot be null or empty");
        }
        this.fromCurrency = fromCurrency;
    }

    public String getToCurrency() {
        return toCurrency;
    }

    public void setToCurrency(String toCurrency) {
        if (toCurrency == null || toCurrency.trim().isEmpty()) {
            throw new IllegalArgumentException("To currency cannot be null or empty");
        }
      this.toCurrency = toCurrency.toUpperCase();
    }

    public BigDecimal getConvertedAmount() {
        return convertedAmount;
    }

    public void setConvertedAmount(BigDecimal convertedAmount) {
        this.convertedAmount = convertedAmount;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        if (timestamp == null) {
            throw new IllegalArgumentException("Timestamp cannot be null");
        }
        this.timestamp = timestamp;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }
    public Double getRateChange() {
        return getRateChange();
    }
    
  /*   public void setRateChange(Double rateChange) {
        this.rateChange = rateChange;
    } */

    // Helper method for formatting
    public String getFormattedConvertedAmount() {
        if (convertedAmount == null || toCurrency == null) {
            return "";
        }
        return String.format("%.2f %s", convertedAmount, toCurrency);
    }

    @Override
    public String toString() {
        return String.format("Conversion[%s %s -> %s %s, rate=%s, time=%s]",
            amount, fromCurrency,
            convertedAmount, toCurrency,
            rate,
            timestamp);
    }

}
