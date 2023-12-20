package com.codersquare.post;

import com.codersquare.request.CreatePostRequest;
import com.codersquare.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "posts")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private long postId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "category")
    private String category;

    @Column(name = "url")
    private String url;

    @Column(name = "points", nullable = false)
    private int points = 0;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    public Post(User user, String title, String category, String url) {
        this.user = user;
        this.title = title;
        this.category = category;
        this.url = url;
        this.points = 0;
        this.createdAt = LocalDateTime.now();
    }

    public Post(CreatePostRequest request) {
        this.title = request.title();
        this.category = request.category();
        this.url = request.url();
        this.points = 0;
        this.createdAt = LocalDateTime.now();
    }
}
