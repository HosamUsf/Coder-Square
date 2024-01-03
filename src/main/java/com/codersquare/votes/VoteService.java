package com.codersquare.votes;

import com.codersquare.comment.CommentRepository;
import com.codersquare.comment.Comment;
import com.codersquare.exceptions.UserAlreadyVoteException;
import com.codersquare.post.Post;
import com.codersquare.post.PostRepository;
import com.codersquare.response.UpdateVoteResponse;
import com.codersquare.user.User;
import com.codersquare.user.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class VoteService {
    // Vote types constants
    public static final String DOWNVOTE = "downvote";
    public static final String UPVOTE = "upvote";

    // Repositories for data access
    private final VoteRepository voteRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;  // Fix: Corrected typo in class name

    /**
     * Process votes for a given user, entity, and vote type.
     *
     * @param userId      The ID of the user.
     * @param entityId    The ID of the entity (Post or Comment).
     * @param entityClass The class type of the entity.
     * @param voteType    The type of vote (upvote or downvote).
     * @return ResponseEntity containing the result of the vote processing.
     */
    public ResponseEntity<UpdateVoteResponse> processVotes(long userId, long entityId, Class<?> entityClass, String voteType) {
        try {
            // Check if the user exists
            checkIfUserExists(userId);

            // Check if the entity (Post or Comment) exists
            checkIfEntityExist(entityId, entityClass);

            // Process the vote and get the vote ID
            long voteId = processEntityVote(userId, entityId, entityClass, voteType);

            // Return success response with vote data
            return ResponseEntity.status(HttpStatus.OK)
                    .body(UpdateVoteResponse.success("Vote processed successfully",
                            new UpdateVoteResponse.VoteData(String.valueOf(userId),
                                    String.valueOf(entityId), String.valueOf(voteId))));
        } catch (UserAlreadyVoteException e) {
            // Return conflict response if the user has already voted
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(UpdateVoteResponse.error(e.getMessage(),
                            new UpdateVoteResponse.ErrorData("USER_ALREADY_VOTED", e.getMessage())));
        } catch (EntityNotFoundException e) {
            // Return bad request response if user or post not found
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(UpdateVoteResponse.error(e.getMessage(),
                            new UpdateVoteResponse.ErrorData("ENTITY_NOT_FOUND", e.getMessage())));
        } catch (Exception e) {
            // Return internal server error response for other exceptions
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(UpdateVoteResponse.error("Internal Server Error",
                            new UpdateVoteResponse.ErrorData("INTERNAL_SERVER_ERROR", e.getMessage())));
        }
    }

    /**
     * Process the vote for a given user, entity, and vote type.
     *
     * @param userId      The ID of the user.
     * @param entityId    The ID of the entity (Post or Comment).
     * @param entityClass The class type of the entity.
     * @param voteType    The type of vote (upvote or downvote).
     * @return The ID of the processed vote.
     */
    private long processEntityVote(long userId, long entityId, Class<?> entityClass, String voteType) {
        // Find the existing vote (if any)
        Vote vote = findVote(userId, entityId, entityClass);

        // Retrieve user and entity information
        User user = userRepository.findById(userId).orElseThrow(() ->
                new EntityNotFoundException("User with Id " + userId + " Doesn't Exist"));
        Object entity = getEntityById(entityId, entityClass);

        // Process the vote based on its existence and type
        if (vote == null) {
            vote =handleNewVote(entity, user, voteType);
        } else {
            handleExistingVote(entity, vote, voteType);
        }

        // Save the vote and return the vote ID
        voteRepository.save(vote);
        return vote.getVoteId();
    }

    /**
     * Handle the processing of a new vote.
     *
     * @param entity   The entity (Post or Comment) being voted on.
     * @param user     The user who is voting.
     * @param voteType The type of vote (upvote or downvote).
     */
    private Vote handleNewVote(Object entity, User user, String voteType) {
        if (UPVOTE.equals(voteType)) {
            updateEntityPoints(entity, 1);
        } else if (DOWNVOTE.equals(voteType)) {
            updateEntityPoints(entity, -1);
        }

        // Create a new vote entity
        return createNewVote(user, voteType, entity);
    }

    /**
     * Handle the processing of an existing vote.
     *
     * @param entity   The entity (Post or Comment) being voted on.
     * @param vote     The existing vote.
     * @param voteType The type of vote (upvote or downvote).
     */
    private void handleExistingVote(Object entity, Vote vote, String voteType) {
        if (vote.getVoteType().equals(voteType)) {
            throw new UserAlreadyVoteException("User has already voted");
        } else if (vote.getVoteType().equals(DOWNVOTE) && UPVOTE.equals(voteType)) {
            updateEntityPoints(entity, 1);
            vote.setVoteType(UPVOTE);
        } else if (vote.getVoteType().equals(UPVOTE) && DOWNVOTE.equals(voteType)) {
            updateEntityPoints(entity, -1);
            vote.setVoteType(DOWNVOTE);
        }
    }

    /**
     * Create a new vote entity.
     *
     * @param user     The user who is voting.
     * @param voteType The type of vote (upvote or downvote).
     * @param entity   The entity (Post or Comment) being voted on.
     * @return The new Vote entity.
     */
    private Vote createNewVote(User user, String voteType, Object entity) {
        Vote vote = new Vote(user, voteType);

        // Set the association based on the entity type
        if (entity instanceof Post post) {
            vote.setPost(post);
        } else if (entity instanceof Comment comment) {
            vote.setComment(comment);
        }

        return vote;
    }


    /**
     * Find the vote for a given user, entity, and entity class.
     *
     * @param userId      The ID of the user.
     * @param entityId    The ID of the entity (Post or Comment).
     * @param entityClass The class type of the entity.
     * @return The Vote entity if found, otherwise null.
     * @throws IllegalArgumentException If the entity class is not supported.
     */
    private Vote findVote(long userId, long entityId, Class<?> entityClass) {
        if (entityClass.equals(Post.class)) {
            return voteRepository.findVoteByUserIdAndPostId(userId, entityId).orElse(null);
        } else if (entityClass.equals(Comment.class)) {
            return voteRepository.findVoteByUserUserIdAndComment_CommentId(userId, entityId).orElse(null);
        }
        throw new IllegalArgumentException("Unsupported entity class: " + entityClass.getName());
    }

    /**
     * Retrieve an entity (Post or Comment) by its ID and class type.
     *
     * @param entityId    The ID of the entity.
     * @param entityClass The class type of the entity.
     * @return The retrieved entity.
     * @throws EntityNotFoundException  If the entity with the given ID is not found.
     * @throws IllegalArgumentException If the entity class is not supported.
     */
    private Object getEntityById(long entityId, Class<?> entityClass) {
        if (entityClass.equals(Post.class)) {
            return postRepository.findById(entityId).orElseThrow(() ->
                    new EntityNotFoundException("Post with Id " + entityId + " Not Found"));
        } else if (entityClass.equals(Comment.class)) {
            return commentRepository.findById(entityId).orElseThrow(() ->
                    new EntityNotFoundException("Comment with Id " + entityId + " Not Found"));
        }
        throw new IllegalArgumentException("Unsupported entity class: " + entityClass.getName());
    }

    /**
     * Update the points of an entity (Post or Comment) based on the provided points value.
     *
     * @param entity The entity to update.
     * @param points The points to add to the entity.
     */
    private void updateEntityPoints(Object entity, int points) {
        if (entity instanceof Post post) {
            post.setPoints(post.getPoints() + points);
            postRepository.save((Post) entity);
        } else if (entity instanceof Comment comment) {
            comment.setPoints(comment.getPoints() + points);
            commentRepository.save((Comment) entity);
        }
    }

    /**
     * Check if an entity (Post or Comment) with the given ID exists.
     *
     * @param entityId    The ID of the entity.
     * @param entityClass The class type of the entity.
     */
    private void checkIfEntityExist(long entityId, Class<?> entityClass) {
        if (entityClass.equals(Post.class)) {
            checkIfPostExists(entityId);
        } else if (entityClass.equals(Comment.class)) {
            checkIfCommentExists(entityId);
        }
    }


    /**
     * Checks if a user exists.
     *
     * @param userId The ID of the user.
     * @throws EntityNotFoundException if the user does not exist.
     */
    private void checkIfUserExists(long userId) {
        if (!userRepository.existsById(userId)) {
            throw new EntityNotFoundException("User with Id " + userId + " Not Found  ");
        }
    }

    /**
     * Checks if a post exists.
     *
     * @param postId The ID of the post.
     * @throws EntityNotFoundException if the post does not exist.
     */
    private void checkIfPostExists(long postId) {
        if (!postRepository.existsByPostId(postId)) {
            throw new EntityNotFoundException("Post with Id " + postId + " Not Found ");
        }
    }

    /**
     * Checks if a comment exists.
     *
     * @param commentId The ID of the comment.
     * @throws EntityNotFoundException if the comment does not exist.
     */
    private void checkIfCommentExists(long commentId) {
        if (!commentRepository.existsByCommentId(commentId)) {
            throw new EntityNotFoundException("Comment with Id " + commentId + " Not Found ");
        }
    }

}
