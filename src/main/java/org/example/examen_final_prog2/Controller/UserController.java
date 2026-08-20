package main.java.org.example.examen_final_prog2.Controller;

<<<<<<< HEAD

import main.java.org.example.examen_final_prog2.Service.UserService;
import main.java.org.example.examen_final_prog2.modul.CashFlow;
import org.springframework.web.bind.annotation.GetMapping;
=======
import main.java.org.example.examen_final_prog2.Service.UserService;
>>>>>>> 189b9abb72b9673b4b3f0bc5cebf86504a8a61f4
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {
    private UserService userService;
    @GetMapping("/users/{id}/cash-flows")
    public List<CashFlow> getAllCashFlow(){
       userService.
    }
}
