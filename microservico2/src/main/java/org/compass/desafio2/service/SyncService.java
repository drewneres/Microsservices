package org.compass.desafio2.service;

import jakarta.transaction.Transactional;
import org.compass.desafio2.client.JsonPlaceholderClient;
import org.compass.desafio2.entity.Comment;
import org.compass.desafio2.entity.Post;
import org.compass.desafio2.repository.CommentRepository;
import org.compass.desafio2.repository.PostRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SyncService {
    private static final Logger logger = LoggerFactory.getLogger(SyncService.class);

    private final JsonPlaceholderClient jsonPlaceholderClient;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;


    public SyncService(JsonPlaceholderClient jsonPlaceholderClient, PostRepository postRepository, CommentRepository commentRepository) {
        this.jsonPlaceholderClient = jsonPlaceholderClient;
        this.postRepository = postRepository;
        this.commentRepository = commentRepository;
    }

    @Transactional
    public void syncPosts() {
        logger.info("Starting post synchronization...");

        // Limpa a tabela de posts
        postRepository.deleteAll();

        List<Post> posts = jsonPlaceholderClient.getAllPosts();
        logger.info("Fetched {} posts from external API.", posts.size());

        postRepository.saveAll(posts);

        logger.info("Post synchronization completed successfully.");
    }

    @Transactional
    public void syncComments() {
        logger.info("Starting post synchronization...");

        // Limpa a tabela de posts
        postRepository.deleteAll();

        List<Comment> comments = jsonPlaceholderClient.getAllComments();
        logger.info("Fetched {} posts from external API.", comments.size());

        commentRepository.saveAll(comments);

        logger.info("Post synchronization completed successfully.");
    }
}