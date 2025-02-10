package org.compass.desafio2.service;

import jakarta.transaction.Transactional;
import org.compass.desafio2.client.JsonPlaceholderClient;
import org.compass.desafio2.entity.Comment;
import org.compass.desafio2.entity.Post;
import org.compass.desafio2.entity.User;
import org.compass.desafio2.repository.CommentRepository;
import org.compass.desafio2.repository.PostRepository;
import org.compass.desafio2.repository.UserRepository;
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
    private final UserRepository userRepository;


    public SyncService(JsonPlaceholderClient jsonPlaceholderClient, PostRepository postRepository, CommentRepository commentRepository, UserRepository userRepository) {
        this.jsonPlaceholderClient = jsonPlaceholderClient;
        this.postRepository = postRepository;
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
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
        logger.info("Starting comments synchronization...");

        // Limpa a tabela de comentários
        commentRepository.deleteAll();

        List<Comment> comments = jsonPlaceholderClient.getAllComments();
        logger.info("Fetched {} comments from external API.", comments.size());

        commentRepository.saveAll(comments);

        logger.info("Comments synchronization completed successfully.");
    }

    @Transactional
    public void syncUsers() {
        logger.info("Starting user synchronization...");

        // Limpa a tabela de usuários
        userRepository.deleteAll();

        List<User> users = jsonPlaceholderClient.getAllUsers();
        logger.info("Fetched {} users from external API.", users.size());

        userRepository.saveAll(users);

        logger.info("Users synchronization completed successfully.");
    }

    @Transactional
    public void syncEverything() {
        logger.info("Starting full data synchronization...");

        try {
            syncPosts();
            syncComments();
            syncUsers();
            logger.info("Full data synchronization completed successfully.");
        } catch (Exception e) {
            logger.error("Error occurred during full data synchronization: {}", e.getMessage(), e);
            throw new RuntimeException("Synchronization failed.", e);
        }
    }

}