package github.sarthakdev143.backend.Service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import github.sarthakdev143.backend.Model.Users;
import github.sarthakdev143.backend.Repository.UsersRepository;

@Service
public class TransactionService {
    @Autowired
    private UsersRepository usersRepository;

    public boolean deposit(String email, String amount) {
        System.out.println("\nInitiating Deposit Of Amount : ₹" + amount + "...\n");
        Optional<Users> user = usersRepository.findByEmail(email);
        if (user.isPresent()) {
            System.out.println("User Found With Email : (" + email + ")");
            System.out.println("User's Name : " + user.get().getAccount_holder_name());

            try {
                // Remove the ₹ symbol from the account balance string
                String accountBalanceStr = user.get().getAccount_balance().replace("₹", "").trim();
                int balance = Integer.parseInt(accountBalanceStr);
                System.out.println("Previous Account Balance : ₹" + balance);

                int amountInt = Integer.parseInt(amount);
                int newBalance = balance + amountInt;

                System.out.println("New Account Balance : ₹" + newBalance);
                user.get().setAccount_balance("₹" + newBalance);
                usersRepository.save(user.get());

                return true;
            } catch (NumberFormatException e) {
                System.out.println("Invalid number format: " + e.getMessage());
                return false;
            }
        }
        System.out.println("User With Email : (" + email + ") Not Found...");
        return false;
    }

    public boolean withdraw(String email, String amount) {
        System.out.println("\nInitiating Withdraw Of Amount : ₹" + amount + "...\n");
        Optional<Users> user = usersRepository.findByEmail(email);
        if (user.isPresent()) {
            System.out.println("User Found With Email : (" + email + ")");
            System.out.println("User's Name : " + user.get().getAccount_holder_name());

            try {
                // Remove the ₹ symbol from the account balance string
                String accountBalanceStr = user.get().getAccount_balance().replace("₹", "").trim();
                int balance = Integer.parseInt(accountBalanceStr);
                System.out.println("Previous Account Balance : ₹" + balance);

                int amountInt = Integer.parseInt(amount);
                int newBalance = balance - amountInt;

                if (newBalance <= -1) {
                    System.out.println("New Account Balance : ₹" + newBalance);
                    System.out.println("Triggering Insufficient Balance!!");
                    return false;
                }

                System.out.println("New Account Balance : ₹" + newBalance);
                user.get().setAccount_balance("₹" + newBalance);
                usersRepository.save(user.get());

                return true;
            } catch (NumberFormatException e) {
                System.out.println("Invalid number format: " + e.getMessage());
                return false;
            }
        }
        System.out.println("User With Email : (" + email + ") Not Found...");
        return false;
    }
}
