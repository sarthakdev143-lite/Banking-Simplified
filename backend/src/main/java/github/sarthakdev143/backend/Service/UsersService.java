package github.sarthakdev143.backend.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import github.sarthakdev143.backend.Exception.UserAlreadyExistsException;
import github.sarthakdev143.backend.Exception.UserNotFoundException;
import github.sarthakdev143.backend.Model.Users;
import github.sarthakdev143.backend.Repository.UsersRepository;

@Service
public class UsersService {

    @Autowired
    private UsersRepository usersRepository;

    public Users createUser(Users users) {
        Optional<Users> existingUser = usersRepository.findByEmail(users.getEmail());
        if (existingUser.isPresent()) {
            throw new UserAlreadyExistsException("Email already registered");
        }
        return usersRepository.save(users);
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
            // update other fields as needed
            return usersRepository.save(existingUser);
        } else {
            throw new UserNotFoundException("User with ID " + id + " not found");
        }
    }

    public void delete(String id) {
        if (usersRepository.existsById(id)) {
            usersRepository.deleteById(id);
        } else {
            throw new UserNotFoundException("User with ID " + id + " not found");
        }
    }
}
