package github.sarthakdev143.backend.Repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import github.sarthakdev143.backend.Model.Users;

@Repository
public interface UsersRepository extends MongoRepository<Users, String> {
    Optional<Users> findByEmail(String email);

}
