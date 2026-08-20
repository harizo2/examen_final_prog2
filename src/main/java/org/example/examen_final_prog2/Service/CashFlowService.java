package main.java.org.example.examen_final_prog2.Service;

import main.java.org.example.examen_final_prog2.Repository.CashFlowRepository;
import main.java.org.example.examen_final_prog2.Request.BalanceResponse;
import main.java.org.example.examen_final_prog2.Request.CreateExpenseRequest;
import main.java.org.example.examen_final_prog2.modul.CashFlow;
import main.java.org.example.examen_final_prog2.modul.Expense;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class CashFlowService {
    private CashFlowRepository cashFlowRepository;

    public List<CashFlow> getCashFlows(String type){
        return cashFlowRepository.findByType(type);
    }

    public List<CashFlow> getCashFlowsById(String id){
        return cashFlowRepository.findByUserId(id);
    }

    public Expense createExpenses(CreateExpenseRequest createExpenseRequest){
        return cashFlowRepository.save(createExpenseRequest);
    }

    public BalanceResponse getBalance(){
        return cashFlowRepository.getBalance();
    }
}
