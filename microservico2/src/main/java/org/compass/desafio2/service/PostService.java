package org.compass.desafio2.service;

import org.compass.desafio2.client.JsonPlaceholderClient;
import org.compass.desafio2.entity.Comment;
import org.compass.desafio2.entity.Post;
import org.compass.desafio2.exception.NotFoundException;
import org.compass.desafio2.repository.CommentRepository;
import org.compass.desafio2.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class PostService {

    private final JsonPlaceholderClient jsonPlaceholderClient;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    public PostService(JsonPlaceholderClient jsonPlaceholderClient,
                       PostRepository postRepository,
                       CommentRepository commentRepository) {
        this.jsonPlaceholderClient = jsonPlaceholderClient;
        this.postRepository = postRepository;
        this.commentRepository = commentRepository;
    }

    @Transactional
    public void syncData() {
        List<Post> posts = jsonPlaceholderClient.getAllPosts();
        postRepository.saveAll(posts);
    }

    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    public Post getPostById(Long id) {
        return postRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Post", String.valueOf(id))
        );
    }

    public List<Comment> getAllCommentsByPostId(Long id) {
        Post post = postRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Post", String.valueOf(id))
        );
        return commentRepository.findByPostId(id);
    }

    public Post savePost(Post post) {
        return postRepository.save(post);
    }

    public Post updatePost(Long id, Post updatedPost) {
        return postRepository.findById(id)
                .map(post -> {
                    post.setTitle(updatedPost.getTitle());
                    post.setBody(updatedPost.getBody());
                    return postRepository.save(post);
                })
                .orElse(null);
    }

    public boolean deletePost(Long id) {
        if (postRepository.existsById(id)) {
            postRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

    public boolean deleteComment(Long postId, Long commentId) {
        Optional<Comment> commentOptional = commentRepository.findById(commentId);

        if (commentOptional.isPresent()) {
            Comment comment = commentOptional.get();

            if (comment.getPostId().equals(postId)) {
                commentRepository.delete(comment);
                return true;
            }
        }
        return false;
    }

}
