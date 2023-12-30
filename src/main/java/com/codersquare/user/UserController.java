package com.codersquare.user;

import com.codersquare.likes.LikeService;
import com.codersquare.post.PostService;
import com.codersquare.response.DeleteEntityResponse;
import com.codersquare.response.PostsDTO;
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
    private final PostService postService;
    private final LikeService likeService;


    @GetMapping("/users")
    public List<UserDTO> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/users/{username}/posts")
    public List<PostsDTO> findAllByUserName(@PathVariable String username) {
        return postService.findPostsWithUserName(username);
    }


    @GetMapping("/users/{username}/likes")
    public List<PostsDTO> findAllUserLikes(@PathVariable String username) {
        return likeService.getUserLikes(username);
    }


    @GetMapping("/users/{userId}")
    public UserDTO getAll(@PathVariable Long userId) {
        return userService.getUserById(userId);
    }

    @DeleteMapping("/users/{username}")
    public ResponseEntity<DeleteEntityResponse> deleteUser(@PathVariable String username) {
        return userService.deleteUser(username);
    }


}
