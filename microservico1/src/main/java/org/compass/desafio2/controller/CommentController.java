package org.compass.desafio2.controller;

import org.compass.desafio2.client.MicrosservicoBClient;
import org.compass.desafio2.model.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/posts/{postId}/comments")
public class CommentController {

    @Autowired
    private MicrosservicoBClient microsservicoBClient;

    @GetMapping
    public List<Comment> getComments() {
        return microsservicoBClient.getComments();
    }

    @PostMapping
    public Comment createComment(@RequestBody Comment comment) {
        return microsservicoBClient.createComment(comment);
    }

    //put, precisa do Dto

    @DeleteMapping("/{id}")
    public void deleteComment(@PathVariable Long id) {
        microsservicoBClient.deleteComment(id);
    }
}
