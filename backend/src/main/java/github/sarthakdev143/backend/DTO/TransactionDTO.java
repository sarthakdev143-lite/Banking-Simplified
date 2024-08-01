package github.sarthakdev143.backend.DTO;

public class TransactionDTO {
    private String email;
    private String amount;

    public TransactionDTO(String email, String amount) {
        this.email = email;
        this.amount = amount;
    }

    public TransactionDTO() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "TransactionDTO [email=" + email + ", amount=" + amount + "]";
    }
}
