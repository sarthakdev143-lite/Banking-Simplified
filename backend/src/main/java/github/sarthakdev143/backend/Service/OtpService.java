package github.sarthakdev143.backend.Service;

import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class OtpService {

    private static final int OTP_LENGTH = 6; // Length of the OTP
    private static final String OTP_CHARACTERS = "0123456789"; // Characters allowed in the OTP
    private static final long OTP_VALIDITY_DURATION = 5 * 60 * 1000; // OTP validity duration in milliseconds (5
                                                                     // minutes)

    // Store OTPs in memory with their expiration times
    private final Map<String, OtpDetails> otpStore = new ConcurrentHashMap<>();

    public String generateOtp(String email) {
        // Generate a random OTP
        StringBuilder otp = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < OTP_LENGTH; i++) {
            otp.append(OTP_CHARACTERS.charAt(random.nextInt(OTP_CHARACTERS.length())));
        }

        // Store OTP with expiration time
        otpStore.put(email, new OtpDetails(otp.toString(), System.currentTimeMillis() + OTP_VALIDITY_DURATION));

        return otp.toString();
    }

    public boolean validateOtp(String email, String enteredOtp) {
        System.out.println("\n\nEntered OTP Validation Method..");
        // Check if the OTP is valid
        OtpDetails otpDetails = otpStore.get(email);
        System.out.println("\nActual OTP : " + otpDetails.getOtp() + "\nEntered OTP : " + enteredOtp + "\n\n");
        if (otpDetails != null && otpDetails.getOtp().equals(enteredOtp)) {
            if (System.currentTimeMillis() <= otpDetails.getExpirationTime()) {
                otpStore.remove(email); // OTP is valid, remove it from the store
                return true;
            } else {
                otpStore.remove(email); // OTP has expired, remove it from the store
            }
        }
        return false;
    }

    private static class OtpDetails {
        private final String otp;
        private final long expirationTime;

        public OtpDetails(String otp, long expirationTime) {
            this.otp = otp;
            this.expirationTime = expirationTime;
        }

        public String getOtp() {
            return otp;
        }

        public long getExpirationTime() {
            return expirationTime;
        }
    }
}
