package com.example.currencyconverter.controller;

import com.example.currencyconverter.model.*;
import com.example.currencyconverter.repository.AlertNotificationRepository;
import com.example.currencyconverter.service.AlertService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Arrays;
import java.util.List;  

@Controller
@RequestMapping("api/v1/alerts")
public class AlertController {
    
    @Autowired
    private AlertService alertService;
    
    @Autowired
    private AlertNotificationRepository notificationRepository;
    
    @GetMapping
    public String showAlertsDashboard(Model model, @RequestParam(required = false) String userId) {
        // For demo purposes, use a default user ID if none provided
        String currentUserId = userId != null ? userId : "default_user";
        
        // Get active alerts for the user
        List<ExchangeRateAlert> activeAlerts = alertService.getUserAlerts(currentUserId);
        
        // Get recent notifications
        List<AlertNotification> notifications = 
            notificationRepository.findByUserIdOrderByTimestampDesc(currentUserId);
        
        // Add available currencies (you should get this from your currency service)
        List<String> availableCurrencies = Arrays.asList("USD", "EUR", "GBP", "JPY", "AUD", "CAD");
        
        model.addAttribute("activeAlerts", activeAlerts);
        model.addAttribute("notifications", notifications);
        model.addAttribute("availableCurrencies", availableCurrencies);
        
        return "alerts-dashboard";
    }

    @PostMapping
    public String createAlert(@ModelAttribute ExchangeRateAlert alert, 
                            RedirectAttributes redirectAttributes) {
        try {
            alert.setUserId("default_user"); // Or get from security context
            alertService.createAlert(alert);
            redirectAttributes.addFlashAttribute("message", "Alert created successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Failed to create alert: " + e.getMessage());
        }
        return "redirect:/api/v1/alerts";
    }

    @PostMapping("/{id}")
    public String updateAlert(@PathVariable Long id, 
                            @RequestParam boolean enabled) {
        ExchangeRateAlert alert = alertService.getAlert(id);
        alert.setEnabled(enabled);
        alertService.updateAlert(alert);
        return "redirect:/api/v1/alerts";
    }

    @PostMapping("/{id}/delete")
    public String deleteAlert(@PathVariable Long id) {
        alertService.deleteAlert(id);
        return "redirect:/api/v1/alerts";
    }
}
