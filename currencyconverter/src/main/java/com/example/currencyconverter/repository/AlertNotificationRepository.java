package com.example.currencyconverter.repository;

import com.example.currencyconverter.model.AlertNotification;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface AlertNotificationRepository extends JpaRepository<AlertNotification, Long> {
    List<AlertNotification> findByUserIdOrderByTimestampDesc(String userId);
    List<AlertNotification> findByUserIdAndReadOrderByTimestampDesc(String userId, boolean read);
}
