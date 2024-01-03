package com.codersquare.votes;

import com.codersquare.comment.Comment;
import com.codersquare.post.Post;
import com.codersquare.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "votes")
public class Vote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "vote_id")
    private long voteId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;


    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne
    @JoinColumn(name = "comment_id")
    private Comment comment;

    @Column(name = "vote_type", nullable = false)
    private String voteType ;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    public Vote(User user, Post post, String voteType) {
        this.user = user;
        this.post = post;
        this.voteType = voteType;
        this.createdAt = LocalDateTime.now();
    }
    public Vote(User user, String voteType) {
        this.user = user;
        this.voteType = voteType;
        this.createdAt = LocalDateTime.now();
    }
}
