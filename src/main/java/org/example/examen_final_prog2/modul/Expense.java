package main.java.org.example.examen_final_prog2.modul;

import java.math.BigDecimal;
import java.time.Instant;

public class Expense extends CashFlow {
    private String reason;
    private Frequency Frenquency;

    public Expense(String id, Instant createdAt, BigDecimal amount, Frequency frenquency, String reason) {
        super(id, createdAt, amount);
        Frenquency = frenquency;
        this.reason = reason;
    }


}
