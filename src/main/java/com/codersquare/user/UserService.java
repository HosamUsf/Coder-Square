package com.codersquare.user;

import com.codersquare.mapper.UserDTOMapper;
import com.codersquare.post.Post;
import com.codersquare.post.PostRepository;
import com.codersquare.response.DeleteEntityResponse;
import com.codersquare.response.UserDTO;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service class for managing users.
 */
@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserDTOMapper userDTOMapper;

    /**
     * Retrieves a list of all users.
     *
     * @return List of UserDTO representing all users
     */
    public List<UserDTO> getAllUsers() {
        return userRepository.findAll().stream()
                .map(userDTOMapper).toList();
    }

    /**
     * Retrieves a user by their ID.
     *
     * @param id User ID
     * @return UserDTO representing the user
     */
    public UserDTO getUserById(Long id) {
        return userRepository.findById(id)
                .map(userDTOMapper)
                .orElseThrow(() -> new EntityNotFoundException("User Doesn't Exist"));
    }

    /**
     * Deletes a user based on the provided username.
     *
     * @param userName Username of the user to be deleted
     * @return ResponseEntity with the status and response body
     */
    @Transactional
    public ResponseEntity<DeleteEntityResponse> deleteUser(String userName) {
        try {
            checkIfUsernameExists(userName);

            userRepository.deleteUserByUserName(userName);
            return ResponseEntity.status(HttpStatus.OK).
                    body(DeleteEntityResponse.success("User", "User Name", userName));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(DeleteEntityResponse.error("User", "User name", userName));
        }
    }

    /**
     * Checks if a user exists.
     *
     * @param username The username of the user.
     * @throws EntityNotFoundException if the user does not exist.
     */
    private void checkIfUsernameExists(String username) {
        if (!userRepository.existsByUserName(username)) {
            throw new EntityNotFoundException("User with user name " + username + " Not Found");
        }
    }
}
