package github.sarthakdev143.backend.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import github.sarthakdev143.backend.Exception.UserNotFoundException;
import github.sarthakdev143.backend.Model.Users;
import github.sarthakdev143.backend.Repository.UsersRepository;
import jakarta.mail.MessagingException;

@Service
public class UsersService {

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private OtpService otpService;

    public void sendOtp(String email) {
        String otp = otpService.generateOtp(email);
        try {
            emailService.sendOtpEmail(email, otp);
            System.out.println("\n\nOTP sent to : " + email + "\nOTP : " + otp + "\n\n");
        } catch (MessagingException e) {
            System.out.println("\n\nError sending OTP to : " + email + "\n\n");
            e.printStackTrace();
        }
    }

    public boolean validateOtp(String email, String otp) {
        return otpService.validateOtp(email, otp);
    }

    public List<Users> findAll() {
        return usersRepository.findAll();
    }

    public Users updateUser(String id, Users updatedUser) {
        Optional<Users> existingUserOptional = usersRepository.findById(id);
        if (existingUserOptional.isPresent()) {
            Users existingUser = existingUserOptional.get();
            existingUser.setAccount_holder_name(updatedUser.getAccount_holder_name());
            existingUser.setAccount_balance(updatedUser.getAccount_balance());
            return usersRepository.save(existingUser);
        } else {
            throw new UserNotFoundException("User with ID " + id + " not found");
        }
    }

    public void delete(String id) {
        if (usersRepository.existsById(id)) {
            usersRepository.deleteById(id);
            System.out.println("User with ID " + id + " deleted successfully.");
        } else {
            throw new UserNotFoundException("User with ID " + id + " not found");
        }
    }
}
