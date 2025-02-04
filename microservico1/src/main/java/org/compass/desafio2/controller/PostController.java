package org.compass.desafio2.controller;

import org.compass.desafio2.client.MicrosservicoBClient;
import org.compass.desafio2.model.Post;
import org.compass.desafio2.model.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    @Autowired
    private MicrosservicoBClient microsservicoBClient;

    @GetMapping
    public List<Post> getPosts() {
        return microsservicoBClient.getPosts();
    }

    @GetMapping("/{id}")
    public Post getPostById(@PathVariable Long id) {
        return microsservicoBClient.getPostById(id);
    }

    @GetMapping("/{id}/comments")
    public List<Comment> getCommentsByPostId(@PathVariable Long id) {
        return microsservicoBClient.getCommentsByPostId(id);
    }
}