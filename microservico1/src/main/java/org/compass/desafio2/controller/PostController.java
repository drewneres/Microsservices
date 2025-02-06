package org.compass.desafio2.controller;

import org.compass.desafio2.client.MicrosservicoBClient;
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
        return microsservicoBClient.getPostById(id);
    }

    @GetMapping("/{id}/comments")
    public List<Comment> getCommentsByPostId(@PathVariable Long id) {
        return microsservicoBClient.getCommentsByPostId(id);
    }
    // POST (Criar Post)
    @PostMapping
    public Post createPost(@RequestBody Post post) {
        return microsservicoBClient.createPost(post);
    }

    // PUT (Atualizar Post Completo)
    @PutMapping("/{id}")
    public Post updatePost(@PathVariable Long id, @RequestBody Post post) {
        return microsservicoBClient.updatePost(id, post);
    }
//
//    // PATCH (Atualizar Parcialmente o Post)
//    @PatchMapping("/{id}")
//    public Post patchPost(@PathVariable Long id, @RequestBody Map<String, Object> updates) {
//        return microsservicoBClient.patchPost(id, updates);
//    }

    // DELETE (Excluir Post)
    @DeleteMapping("/{id}")
    public void deletePost(@PathVariable Long id) {
        microsservicoBClient.deletePost(id);
    }
}