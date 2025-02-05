package org.compass.desafio2.service;

import org.compass.desafio2.client.JsonPlaceholderClient;
import org.compass.desafio2.entity.Comment;
import org.compass.desafio2.entity.Post;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService {

    private final JsonPlaceholderClient jsonPlaceholderClient;


    public PostService(JsonPlaceholderClient jsonPlaceholderClient) {
        this.jsonPlaceholderClient = jsonPlaceholderClient;
    }

    public List<Post> getAllPosts() {
        return jsonPlaceholderClient.getAllPosts();
    }

    public Post getPostById(Long id) {
        return jsonPlaceholderClient.getPostById(id);
    }

    public List<Comment> getAllCommentsByPostId(Long id) {
        return jsonPlaceholderClient.getCommentsByPostId(id);
    }

}
