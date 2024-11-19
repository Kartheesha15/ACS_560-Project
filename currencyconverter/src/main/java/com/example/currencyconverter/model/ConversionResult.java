package com.example.currencyconverter.model;

import java.time.LocalDateTime;
import java.math.BigDecimal;

public class ConversionResult {
    //private Long id;                  
    private BigDecimal amount;        
    private String fromCurrency;      
    private String toCurrency;        
    private BigDecimal convertedAmount; 
    private BigDecimal rate;          
    private LocalDateTime timestamp;  
    private String status;            
    private String errorMessage;      

    // Default constructor
    public ConversionResult() {
        this.timestamp = LocalDateTime.now();
        this.status = "SUCCESS";
    }

    // Constructor with main fields
    public ConversionResult(BigDecimal amount, String fromCurrency, String toCurrency) {
        this();
        this.amount = amount;
        this.fromCurrency = fromCurrency;
        this.toCurrency = toCurrency;
    }

    // Getters and Setters
  /*   public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    } */

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getFromCurrency() {
        return fromCurrency;
    }

    public void setFromCurrency(String fromCurrency) {
        this.fromCurrency = fromCurrency != null ? fromCurrency.toUpperCase() : null;;
    }

    public String getToCurrency() {
        return toCurrency;
    }

    public void setToCurrency(String toCurrency) {
        this.toCurrency = toCurrency != null ? toCurrency.toUpperCase() : null;;
    }

    public BigDecimal getConvertedAmount() {
        return convertedAmount;
    }

    public void setConvertedAmount(BigDecimal convertedAmount) {
        this.convertedAmount = convertedAmount;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        if (status == null || status.trim().isEmpty()) {
            throw new IllegalArgumentException("Status cannot be null or empty");
        }
        this.status = status;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
        if (errorMessage != null && !errorMessage.isEmpty()) {
            this.status = "ERROR";
        }
    }

    // Helper methods
    public boolean isSuccessful() {
        return "SUCCESS".equals(status);
    }

    public String getFormattedConvertedAmount() {
        if (convertedAmount == null || toCurrency == null) {
            return ""; // Return empty string instead of throwing exception
        }
        return String.format("%.2f %s", convertedAmount, toCurrency);
    }

    public String getFormattedRate() {
        if (rate == null) {
            return ""; // Return empty string instead of throwing exception
        }
        return String.format("%.6f", rate);
    }

    @Override
    public String toString() {
        return String.format("ConversionResult[%s %s -> %s %s, rate=%s, time=%s]",
            amount, fromCurrency,
            convertedAmount, toCurrency,
            rate,
            timestamp);
    }
}