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
    private String userName;
    @Column(name = "password", nullable = false)
    private String password;
    @Column(name = "email", nullable = false, unique = true)
    private String email;
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;


    public User(RegistrationRequest request) {
        this.firstName = request.firstName();
        this.lastName = request.lastName();
        this.userName = request.userName();
        this.password = request.password();
        this.email = request.email();
        this.createdAt = LocalDateTime.now();
    }

    public User(String firstName, String lastName, String userName, String password, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.password = password;
        this.email = email;
        this.createdAt = LocalDateTime.now();
    }
}
