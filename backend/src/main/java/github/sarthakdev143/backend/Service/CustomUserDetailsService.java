package github.sarthakdev143.backend.Service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import github.sarthakdev143.backend.Model.Users;
import github.sarthakdev143.backend.Repository.UsersRepository;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UsersRepository userRepository;

    public CustomUserDetailsService(UsersRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("\nLoading User By Username: " + username);
        Optional<Users> userOptional = userRepository.findByEmail(username);
        if (userOptional.isEmpty()) {
            System.out.println("\nUser Not Found\n");
            throw new UsernameNotFoundException("User not found");
        }

        Users user = userOptional.get();
        Set<String> roles = new HashSet<>();
        user.getRoles().forEach(role -> roles.add(role.getName()));

        System.out.println("\nUser Found: " + user.toString());
        return org.springframework.security.core.userdetails.User
                .withUsername(user.getAccount_holder_name())
                .password(user.getPassword())
                .roles(roles.toArray(new String[0]))
                .build();
    }
}
