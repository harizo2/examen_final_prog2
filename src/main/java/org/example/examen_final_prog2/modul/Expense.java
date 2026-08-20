package main.java.org.example.examen_final_prog2.modul;

import lombok.AllArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;

@AllArgsConstructor
public class Expense extends CashFlow {
    private String reason;
    private Frequency Frenquency;

}
