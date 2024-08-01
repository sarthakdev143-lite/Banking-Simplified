package github.sarthakdev143.backend.Controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import github.sarthakdev143.backend.DTO.TransactionDTO;
import github.sarthakdev143.backend.Service.TransactionService;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @PostMapping("/deposit")
    public ResponseEntity<Map<String, Object>> depositMoney(@RequestBody TransactionDTO transactionDTO) {
        System.out.println("\n\nEntered OTP Verification Method..\n");
        String email = transactionDTO.getEmail();
        String amount = transactionDTO.getAmount();
        System.out.println("Credentials Received in Backend : \nAmount : " + amount + "\nEmail : " + email);
        if (transactionService.deposit(email, amount)) {
            return new ResponseEntity<>(Map.of("success", true, "message", "Transaction Successfull"), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(Map.of("success", false, "message", "Transaction Failed"), HttpStatus.BAD_REQUEST);
        }
    }
    
    @PostMapping("/withdraw")
    public ResponseEntity<Map<String, Object>> withdrawMoney(@RequestBody TransactionDTO transactionDTO) {
        System.out.println("\n\nEntered OTP Verification Method..\n");
        String email = transactionDTO.getEmail();
        String amount = transactionDTO.getAmount();
        System.out.println("Credentials Received in Backend : \nAmount : " + amount + "\nEmail : " + email);
        if (transactionService.withdraw(email, amount)) {
            return new ResponseEntity<>(Map.of("success", true, "message", "Transaction Successfull"), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(Map.of("success", false, "message", "Transaction Failed"), HttpStatus.BAD_REQUEST);
        }
    }
}
