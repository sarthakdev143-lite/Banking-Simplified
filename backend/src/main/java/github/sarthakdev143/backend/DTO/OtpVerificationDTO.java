package github.sarthakdev143.backend.DTO;

public class OtpVerificationDTO {
    private String email;
    private String otp;

    public OtpVerificationDTO() {
    }

    public OtpVerificationDTO(String email, String otp) {
        this.email = email;
        this.otp = otp;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    @Override
    public String toString() {
        return "OtpVerificationDTO [email=" + email + ", otp=" + otp + "]";
    }
}
