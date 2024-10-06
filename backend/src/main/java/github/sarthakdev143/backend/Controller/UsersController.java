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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import github.sarthakdev143.backend.DTO.VerifyOtpRequestDTO;
import github.sarthakdev143.backend.Exception.UserAlreadyExistsException;
import github.sarthakdev143.backend.Model.Users;
import github.sarthakdev143.backend.Repository.UsersRepository;
import github.sarthakdev143.backend.Service.UsersService;

@RestController
@CrossOrigin(origins = "https://sarthakdev-banking.netlify.app/")
// @CrossOrigin(origins = "http://localhost:3000/")
@RequestMapping("/api")
public class UsersController {

    @Autowired
    private UsersService usersService;

    @Autowired
    private UsersRepository usersRepository;

    @PostMapping("/create")
    public ResponseEntity<String> sendOtp(@RequestBody Users user) {
        if (user.getEmail() == null || user.getEmail().isEmpty()) {
            System.out.println("Invalid Email..");
            return new ResponseEntity<>("Invailid Email..", HttpStatus.BAD_REQUEST);
        }

        Optional<Users> existingUser = usersRepository.findByEmail(user.getEmail());
        if (existingUser.isPresent()) {
            System.out.println("User with email (" + user.getEmail() + ") already exists.");
            return new ResponseEntity<>("User already exists", HttpStatus.CONFLICT);
        }

        try {
            usersService.sendOtp(user.getEmail());
            return new ResponseEntity<>("OTP sent to " + user.getEmail(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to send OTP", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/verify-otp")
    public ResponseEntity<String> verifyOtp(@RequestBody VerifyOtpRequestDTO requestDTO) {
        System.out.println("\n\nVerifying OTP..");
        Users user = requestDTO.getUser();
        String otp = requestDTO.getOtp();
        try {
            boolean isValid = usersService.validateOtp(user.getEmail(), otp);
            if (isValid) {
                user.setAccount_balance("₹0");
                usersRepository.save(user);
                return new ResponseEntity<>("OTP verified", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Invalid OTP", HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to verify OTP",
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/check-user")
    public ResponseEntity<String> sendOtpIfUserExists(@RequestParam String email) {
        System.out.println("\n\nChecking User..");
        if (email == null || email.isEmpty()) {
            System.out.println("\n\nEmail Not Registered..\n\n");
            return new ResponseEntity<>("Email Not Found! 404", HttpStatus.BAD_REQUEST);
        }

        Optional<Users> existingUser = usersRepository.findByEmail(email);
        if (existingUser.isPresent()) {
            System.out.println("\n\nUser Found..!!\n\n");
            return new ResponseEntity<>("User Found", HttpStatus.OK);
        } else {
            System.out.println("\n\nUser not Found\n\n");
            return new ResponseEntity<>("User not found! 404", HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/check-password")
    public ResponseEntity<Boolean> checkPassword(@RequestParam String email, @RequestParam String password) {
        if (email == null || email.isEmpty() || password == null || password.isEmpty()) {
            return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
        }

        Optional<Users> user = usersRepository.findByEmail(email);
        if (user.isPresent()) {
            String userPassword = user.get().getPassword();
            System.out.println(
                    "\n\nCurrent User Password : " + userPassword + "\nEntered Password : " + password + "\n\n");
            if (password.equals(userPassword)) {
                System.out.println("\nPassword Matched!\n\n");
                return new ResponseEntity<>(true, HttpStatus.OK);
            } else {
                System.out.println("\nPassword Not Matched!\n\n");
                return new ResponseEntity<>(false, HttpStatus.OK);
            }
        } else {
            return new ResponseEntity<>(false, HttpStatus.NOT_FOUND);
        }
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<String> handleUserAlreadyExistsException(UserAlreadyExistsException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.CONFLICT);
    }
}

@RestController
class LiveController {

    @GetMapping("/")
    public String redirectLive() {
        return "redirect:/live";
    }
    @GetMapping("/live")
    public ResponseEntity<String> checkIfServerIsLive() {
        return new ResponseEntity<>("Server is Live!", HttpStatus.OK);
    }
}
