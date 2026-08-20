package main.java.org.example.examen_final_prog2.Request;

import main.java.org.example.examen_final_prog2.modul.Frequency;

import java.math.BigDecimal;

public class CreateExpenseRequest {
    private String userId;
    private BigDecimal amount;
    private String reason;
    private Frequency frequency;

    // Getters et setters (nécessaires pour la désérialisation JSON)
    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }

    public String getReason() { return reason; }
    public void setReason(String reason) { this.reason = reason; }

    public Frequency getFrequency() { return frequency; }
    public void setFrequency(Frequency frequency) { this.frequency = frequency; }
}