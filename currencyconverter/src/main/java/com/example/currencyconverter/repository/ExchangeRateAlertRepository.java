package com.example.currencyconverter.repository;

import com.example.currencyconverter.model.ExchangeRateAlert;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ExchangeRateAlertRepository extends JpaRepository<ExchangeRateAlert, Long> {
    List<ExchangeRateAlert> findByUserId(String userId);
    List<ExchangeRateAlert> findByEnabled(boolean enabled);
}
