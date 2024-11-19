package com.example.currencyconverter.service;

import com.example.currencyconverter.model.*;
import com.example.currencyconverter.repository.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;


@Service
public class AlertService {
    
    @Autowired
    private ExchangeRateAlertRepository alertRepository;
    
    @Autowired
    private AlertNotificationRepository notificationRepository;
    
    @Autowired
    private CurrencyConverterService converterService;

    public ExchangeRateAlert createAlert(ExchangeRateAlert alert) {
        alert.setLastChecked(LocalDateTime.now());
        alert.setEnabled(true);
        return alertRepository.save(alert);
    }

    public ExchangeRateAlert updateAlert(ExchangeRateAlert alert) {
        return alertRepository.save(alert);
    }

    public void deleteAlert(Long alertId) {
        alertRepository.deleteById(alertId);
    }

    public List<ExchangeRateAlert> getUserAlerts(String userId) {
        return alertRepository.findByUserId(userId);
    }

    @Scheduled(fixedRateString = "${alert.check.interval}")
    public void checkExchangeRates() {
        List<ExchangeRateAlert> enabledAlerts = alertRepository.findByEnabled(true);
        
        for (ExchangeRateAlert alert : enabledAlerts) {
            try {
                double currentRate = converterService.convert(1.0, 
                    alert.getFromCurrency(), 
                    alert.getToCurrency());
                
                BigDecimal newRate = BigDecimal.valueOf(currentRate);
                BigDecimal oldRate = alert.getLastRate();
                
                if (oldRate != null) {
                    @SuppressWarnings("deprecation")
                    BigDecimal changePercentage = newRate.subtract(oldRate)
                        .divide(oldRate, 4, BigDecimal.ROUND_HALF_UP)
                        .multiply(BigDecimal.valueOf(100))
                        .abs();
                    
                    if (changePercentage.compareTo(alert.getThreshold()) >= 0) {
                        createNotification(alert, oldRate, newRate, changePercentage);
                    }
                }
                
                alert.setLastRate(newRate);
                alert.setLastChecked(LocalDateTime.now());
                alertRepository.save(alert);
                
            } catch (Exception e) {
                // Log the error and continue with next alert
                System.err.println("Error checking alert " + alert.getId() + ": " + e.getMessage());
            }
        }
    }

    private void createNotification(ExchangeRateAlert alert, BigDecimal oldRate, 
                                  BigDecimal newRate, BigDecimal changePercentage) {
        AlertNotification notification = new AlertNotification();
        notification.setAlert(alert);
        notification.setUserId(alert.getUserId());
        notification.setType("RATE_CHANGE");
        notification.setMessage(String.format(
            "Exchange rate for %s/%s changed by %.2f%%. Old rate: %.4f, New rate: %.4f",
            alert.getFromCurrency(),
            alert.getToCurrency(),
            changePercentage,
            oldRate,
            newRate
        ));
        
        notificationRepository.save(notification);
    }
    public ExchangeRateAlert getAlert(Long id) {
        return alertRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Alert not found"));
    }
}