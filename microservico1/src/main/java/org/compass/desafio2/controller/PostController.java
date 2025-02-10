package org.compass.desafio2.controller;

import org.compass.desafio2.client.MicrosservicoBClient;
import org.compass.desafio2.exception.EntityNotFoundException;
import org.compass.desafio2.exception.UniqueViolationException;
import org.compass.desafio2.model.Post;
import org.compass.desafio2.model.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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
        try{
            return microsservicoBClient.getPostById(id);
        }
        catch (RuntimeException e) {
            throw new EntityNotFoundException("Post not found");
        }
    }

    @GetMapping("/{id}/comments")
    public List<Comment> getCommentsByPostId(@PathVariable Long id) {
        try {
            return microsservicoBClient.getCommentsByPostId(id);
        }
        catch (RuntimeException e) {
            throw new EntityNotFoundException("Post not found");
        }
    }

    @PostMapping
    public Post createPost(@RequestBody Post post) {
        try {
            return microsservicoBClient.createPost(post);
        }
        catch (RuntimeException e) {
            throw new UniqueViolationException("Post Id already exists");
        }
    }


    @PutMapping("/{id}")
    public Post updatePost(@PathVariable Long id, @RequestBody Post post) {
        return microsservicoBClient.updatePost(id, post);
    }

    @DeleteMapping("/{id}")
    public void deletePost(@PathVariable Long id) {
        try {
            microsservicoBClient.deletePost(id);
        }
        catch (RuntimeException e) {
            throw new EntityNotFoundException("Post not found");
        }
    }
}