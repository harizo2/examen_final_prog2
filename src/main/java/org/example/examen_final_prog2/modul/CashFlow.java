package main.java.org.example.examen_final_prog2.modul;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.time.Instant;

@AllArgsConstructor
public class CashFlow {
    private String id;
    private Instant createdAt;
    private BigDecimal amount;


}
