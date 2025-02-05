package org.compass.desafio2.service;

import org.compass.desafio2.client.JsonPlaceholderClient;
import org.compass.desafio2.entity.Comment;
import org.compass.desafio2.entity.Post;
import org.compass.desafio2.repository.CommentRepository;
import org.compass.desafio2.repository.PostRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
        return postRepository.findById(id).orElse(null);
    }

    public List<Comment> getAllCommentsByPostId(Long id) {
        return commentRepository.findAll();
    }
}
