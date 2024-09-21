package github.sarthakdev143.backend.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import github.sarthakdev143.backend.Model.Roles;

@Repository
public interface RolesRepository extends MongoRepository<Roles, String> {
    
}
