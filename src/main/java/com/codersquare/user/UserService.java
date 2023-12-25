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

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final UserDTOMapper userDTOMapper;


    public List<UserDTO> getAllUsers() {
        return userRepository.findAll().stream()
                .map(userDTOMapper).toList();

    }


    public UserDTO getUserById(Long id) {
        return userRepository.findById(id)
                .map(userDTOMapper)
                .orElseThrow(() -> new EntityNotFoundException("User Doesn't Exist"));
    }

    @Transactional
    public ResponseEntity<DeleteEntityResponse> deleteUser(String userName) {
        try {
            checkIfUsernameExists(userName);


            User user = userRepository.findUserByUserName(userName).orElse(null);
            if (user != null) {
                List<Post> userPosts = postRepository.findAllByUserId(user.getUserId());
                postRepository.deleteAll(userPosts);
            }
            userRepository.deleteUserByUserName(userName);
            return ResponseEntity.status(HttpStatus.OK).
                    body(DeleteEntityResponse.success("User", "User Name", userName));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(DeleteEntityResponse.error("User", "User name", userName));
        }
    }


    private void checkIfUsernameExists(String username) {
        if (!userRepository.existsByUserName(username)) {
            throw new EntityNotFoundException("User with user name " + username + " Not Found");
        }
    }


}
