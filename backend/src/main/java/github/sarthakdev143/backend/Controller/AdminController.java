package github.sarthakdev143.backend.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import github.sarthakdev143.backend.Exception.UserNotFoundException;
import github.sarthakdev143.backend.Model.Users;
import github.sarthakdev143.backend.Service.UsersService;

@RestController
@RequestMapping("/api/admin-panel")
@CrossOrigin(origins = "https://sarthakdev-banking.netlify.app/")
// @CrossOrigin(origins = "http://localhost:3000/")
public class AdminController {

    @Autowired
    private UsersService usersService;

    @GetMapping
    public List<Users> adminHome() {
        return usersService.findAll();
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<Users> editUser(@PathVariable("id") String id, @RequestBody Users users) {
        try {
            Users updatedUser = usersService.updateUser(id, users);
            return ResponseEntity.ok(updatedUser);
        } catch (UserNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable("id") String id) {
        try {
            usersService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (UserNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
