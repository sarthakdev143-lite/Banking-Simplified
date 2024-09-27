package github.sarthakdev143.backend.Controller;

import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import github.sarthakdev143.backend.DTO.OtpVerificationDTO;
import github.sarthakdev143.backend.Model.Users;
import github.sarthakdev143.backend.Repository.UsersRepository;
import github.sarthakdev143.backend.Service.EmailService;
import github.sarthakdev143.backend.Service.OtpService;

@RestController
// @CrossOrigin(origins = "https://sarthakdev-banking.netlify.app/")
@CrossOrigin(origins = "http://localhost:3000/")
@RequestMapping("/api")
public class OtpController {

    @Autowired
    private OtpService otpService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private UsersRepository usersRepository;

    @PostMapping("/send-transaction-otp")
    public ResponseEntity<String> sendOtp(@RequestBody Map<String, String> request) {
        try {
            String email = request.get("email");
            if (email == null || email.isEmpty()) {
                return new ResponseEntity<>("Email is required", HttpStatus.BAD_REQUEST);
            }

            Optional<Users> existingUser = usersRepository.findByEmail(email);
            if (existingUser.isPresent()) {
                String otp = otpService.generateOtp(email);
                emailService.sendOtpEmail(email, otp);
                System.out.println("\n\nOTP: " + otp);
                return new ResponseEntity<>("OTP sent successfully", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            System.err.println("Error sending OTP: " + e.getMessage());
            return new ResponseEntity<>("Error sending OTP", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/verify-trasaction-otp")
    public ResponseEntity<Map<String, Object>> verifyOtp(@RequestBody OtpVerificationDTO otpRequest) {
        System.out.println("\n\nEntered OTP Verification Method..\n");
        String email = otpRequest.getEmail();
        String otp = otpRequest.getOtp();
        System.out.println("Credentials Received in Backend : \nOTP : " + otp + "\nEmail : " + email);
        if (otpService.validateOtp(email, otp)) {
            return new ResponseEntity<>(Map.of("success", true, "message", "OTP verified successfully"), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(Map.of("success", false, "message", "Invalid OTP"), HttpStatus.BAD_REQUEST);
        }
    }
}
