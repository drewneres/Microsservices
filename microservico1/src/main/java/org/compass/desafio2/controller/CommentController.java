package org.compass.desafio2.controller;

import org.compass.desafio2.client.MicrosservicoBClient;
import org.compass.desafio2.model.Comment;
import org.compass.desafio2.web.dto.CommentDto;
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

    @PutMapping("/{id}")
    public Comment updateComment(@PathVariable Long id, @RequestBody CommentDto commentDto) {
        return microsservicoBClient.updateComment(id, commentDto);
    }

    @DeleteMapping("/{id}")
    public void deleteComment(@PathVariable Long id) {
        microsservicoBClient.deleteComment(id);
    }
}
