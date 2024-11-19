package com.example.currencyconverter.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "exchange_rate_alerts")
public class ExchangeRateAlert {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fromCurrency;
    private String toCurrency;
    private BigDecimal threshold;
    private BigDecimal lastRate;
    private LocalDateTime lastChecked;
    private boolean enabled;
    private String userId;  // If you have user authentication

    public ExchangeRateAlert() {
        this.lastChecked = LocalDateTime.now();
        this.enabled = true;
    }

    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getFromCurrency() { return fromCurrency; }
    public void setFromCurrency(String fromCurrency) { this.fromCurrency = fromCurrency; }
    
    public String getToCurrency() { return toCurrency; }
    public void setToCurrency(String toCurrency) { this.toCurrency = toCurrency; }
    
    public BigDecimal getThreshold() { return threshold; }
    public void setThreshold(BigDecimal threshold) { this.threshold = threshold; }
    
    public BigDecimal getLastRate() { return lastRate; }
    public void setLastRate(BigDecimal lastRate) { this.lastRate = lastRate; }
    
    public LocalDateTime getLastChecked() { return lastChecked; }
    public void setLastChecked(LocalDateTime lastChecked) { this.lastChecked = lastChecked; }
    
    public boolean isEnabled() { return enabled; }
    public void setEnabled(boolean enabled) { this.enabled = enabled; }
    
    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }
} 