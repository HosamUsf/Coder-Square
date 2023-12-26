package com.codersquare.comment;

import com.codersquare.post.Post;
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
@Table(name = "comments")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long commentId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    @Column(name = "text", nullable = false)
    private String text;

    @Column(name = "points", nullable = false)
    private int points = 0;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    // test
    public Comment(String text) {
        this.text = text;
        this.points = 0;
        this.createdAt = LocalDateTime.now();
    }
}
