package com.codersquare.user;

import com.codersquare.response.DeleteEntityResponse;
import com.codersquare.response.UserDTO;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("api/v1")
public class UserController {
    private final UserService userService;

    @GetMapping("/users")
    public List<UserDTO> getAllUsers() {
        return userService.getAllUsers();
    }


    @GetMapping("/users/{userId}")
    public UserDTO getAll(@PathVariable Long userId) {
        return userService.getUserById(userId);
    }

    @DeleteMapping("/users/{userName}")
    public ResponseEntity<DeleteEntityResponse> deleteUser(@PathVariable String userName) {
        return userService.deleteUser(userName);
    }


}
