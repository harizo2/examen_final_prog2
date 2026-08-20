package main.java.org.example.examen_final_prog2.Request;

import java.math.BigDecimal;

public class BalanceResponse {
    private BigDecimal totalDonations;
    private BigDecimal totalExpenses;
    private BigDecimal balance;

    public BalanceResponse(BigDecimal totalDonations, BigDecimal totalExpenses, BigDecimal balance) {
        this.totalDonations = totalDonations;
        this.totalExpenses = totalExpenses;
        this.balance = balance;
    }

    public BigDecimal getTotalDonations() { return totalDonations; }
    public BigDecimal getTotalExpenses() { return totalExpenses; }
    public BigDecimal getBalance() { return balance; }
}