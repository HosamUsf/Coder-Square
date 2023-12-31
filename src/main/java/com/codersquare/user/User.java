package com.codersquare.user;

import com.codersquare.post.Post;
import com.codersquare.request.RegistrationRequest;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User {
    @OneToMany(cascade = CascadeType.ALL,
            mappedBy = "user", orphanRemoval = true, fetch = FetchType.EAGER)
    List<Post> posts = new ArrayList<>();

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private long userId;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "locked")
    private boolean locked = false;

    @Column(name = "enabled")
    private boolean enabled = false;

    public User(RegistrationRequest request) {
        this.firstName = request.firstName();
        this.lastName = request.lastName();
        this.username = request.userName();
        this.password = request.password();
        this.email = request.email();
        this.createdAt = LocalDateTime.now();
    }

    public User(String firstName, String lastName, String username, String password, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.email = email;
        this.createdAt = LocalDateTime.now();
        this.locked = false;
        this.enabled = false;

    }

    public User(User user) {
        this.posts = user.getPosts();
        this.userId = user.getUserId();
        this.firstName = user.getUsername();
        this.lastName = user.getLastName();
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.email = user.getEmail();
        this.createdAt = user.getCreatedAt();
        this.locked = user.isLocked();
        this.enabled = user.isEnabled();
    }
}
