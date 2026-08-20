package main.java.org.example.examen_final_prog2.modul;

import java.math.BigDecimal;
import java.time.Instant;


public class Donation extends CashFlow {
    private String comment;

    public Donation(String id, Instant createdAt, BigDecimal amount, String comment) {
        super(id, createdAt, amount);
        this.comment = comment;
    }
}
