package github.sarthakdev143.backend.Model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Document
public class Users {
    @Id
    private String id;
    private String account_holder_name;
    private String email;
    private String password;    
    private String account_balance;
}
