package github.sarthakdev143.backend.Controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import github.sarthakdev143.backend.Exception.UserAlreadyExistsException;
import github.sarthakdev143.backend.Model.Users;
import github.sarthakdev143.backend.Repository.UsersRepository;
import github.sarthakdev143.backend.Service.UsersService;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class UsersController {

    @Autowired
    private UsersService usersService;

    @Autowired
    private UsersRepository usersRepository;

    @PostMapping("/create")
    public ResponseEntity<Users> createUser(@RequestBody Users users) {
        try {
            users.setAccount_balance("₹0");
            Users createdUser = usersService.createUser(users);
            return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
        } catch (UserAlreadyExistsException e) {
            return new ResponseEntity<>(null, HttpStatus.CONFLICT);
        }
    }

    @GetMapping("/check-user")
    public ResponseEntity<String> sendOtp(@RequestParam String email) {
        if (email == null || email.isEmpty()) {
            // Bad request, email is missing or invalid
            System.out.println("Email Not Registered..");
            return new ResponseEntity<>("Email Not Found! 404", HttpStatus.BAD_REQUEST);
        }

        Optional<Users> existingUser = usersRepository.findByEmail(email);
        if (existingUser.isPresent()) {
            System.out.println("\n\nUser Found..!!\n\n");
            return new ResponseEntity<>("User Found", HttpStatus.OK);
        } else {
            // Bad request, email is missing or invalid
            System.out.println("User not Found");
            return new ResponseEntity<>("User not found! 404", HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/check-password")
    public Boolean checkPassword(@RequestParam String email, @RequestParam String password) {
        if (email == null || email.isEmpty() || password == null || password.isEmpty()) {
            // Bad request, email is missing or invalid
            return false;
        }

        Optional<Users> user = usersRepository.findByEmail(email);
        String userPassword = user.get().getPassword();
        System.out.println("\n\n\nCurrect User Password : " + userPassword + "\nEntered Password : " + password);
        if (password.equals(userPassword)) {
            System.out.println("Password Matched!\n\n\n");
            return true;
        } else
            System.out.println("Password Not Matched!\n\n\n");
        return false;
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<String> handleUserAlreadyExistsException(UserAlreadyExistsException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.CONFLICT);
    }
}
