package github.sarthakdev143.backend.DTO;

import github.sarthakdev143.backend.Model.Users;

public class VerifyOtpRequestDTO {
    private Users user;
    private String otp;

    public VerifyOtpRequestDTO(Users user, String otp) {
        this.user = user;
        this.otp = otp;
    }

    public VerifyOtpRequestDTO() {
    }

    // Getters and setters
    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    @Override
    public String toString() {
        return "VerifyOtpRequestDTO [user=" + user + ", otp=" + otp + "]";
    }
}
