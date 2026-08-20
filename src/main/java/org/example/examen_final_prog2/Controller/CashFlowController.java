package main.java.org.example.examen_final_prog2.Controller;

import main.java.org.example.examen_final_prog2.Repository.CashFlowRepository;
import main.java.org.example.examen_final_prog2.Request.BalanceResponse;
import main.java.org.example.examen_final_prog2.Request.CreateExpenseRequest;
import main.java.org.example.examen_final_prog2.Service.CashFlowService;
import main.java.org.example.examen_final_prog2.modul.CashFlow;
import main.java.org.example.examen_final_prog2.modul.Expense;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CashFlowController {
    private CashFlowService cashFlowService;

    @GetMapping("/cash-flows/{type}")
    public List<CashFlow> cashFlowList (@PathVariable String type){return cashFlowService.getCashFlows(type);}

    @GetMapping("/users/{id}/cash-flows")
    public List<CashFlow> cashFlows (@PathVariable String id){return cashFlowService.getCashFlowsById(id);}

    @PostMapping("/expenses")
    public ResponseEntity<?> createExpense(@RequestBody CreateExpenseRequest request) {

        if (request.getUserId() == null || request.getUserId().isBlank()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("userId est requis");
        }
        if (request.getAmount() == null || request.getAmount().signum() <= 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("amount doit être positif");
        }

        Expense expense = cashFlowService.createExpenses(request);

        if (expense == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erreur lors de la création de la dépense");
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(expense);
    }


        @GetMapping("/balance")
        public ResponseEntity<BalanceResponse> getBalance() {
            BalanceResponse balance = cashFlowService.getBalance();
            return ResponseEntity.ok(balance);
        }
}
