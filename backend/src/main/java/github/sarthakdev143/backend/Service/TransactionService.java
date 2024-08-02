package github.sarthakdev143.backend.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import github.sarthakdev143.backend.Model.Users;
import github.sarthakdev143.backend.Repository.UsersRepository;
import jakarta.mail.MessagingException;

@Service
public class TransactionService {

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private EmailService emailService;

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

                // Send transaction email
                sendTransactionEmail(user.get(), "deposit", amountInt);

                return true;
            } catch (NumberFormatException | MessagingException e) {
                System.out.println("Error: " + e.getMessage());
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

                // Send transaction email
                sendTransactionEmail(user.get(), "withdraw", amountInt);

                return true;
            } catch (NumberFormatException | MessagingException e) {
                System.out.println("Error: " + e.getMessage());
                return false;
            }
        }
        System.out.println("User With Email : (" + email + ") Not Found...");
        return false;
    }

    private void sendTransactionEmail(Users user, String type, int amount) throws MessagingException {
        String transactionId = "TXN" + System.currentTimeMillis(); // Generate a transaction ID
        String date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        emailService.sendTransactionEmail(user.getEmail(), transactionId, amount, date, type);
    }
}
