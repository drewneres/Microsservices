package org.compass.desafio2.client;

import org.compass.desafio2.model.Post;
import org.compass.desafio2.model.Comment;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "microsservico-b", url = "${microsservico-b.url}")
public interface MicrosservicoBClient {

    @GetMapping("/api/posts")
    List<Post> getPosts();

    @GetMapping("/api/posts/{id}")
    Post getPostById(@PathVariable Long id);

    @GetMapping("/api/posts/{id}/comments")
    List<Comment> getCommentsByPostId(@PathVariable Long id);

        // POST
    @PostMapping("/api/posts")
    Post createPost(@RequestBody Post post);

    // PUT (atualização completa)
    @PutMapping("/api/posts/{id}")
    Post updatePost(@PathVariable Long id, @RequestBody Post post);

//    // PATCH (atualização parcial)
//    @PatchMapping("/api/posts/{id}")
//    Post patchPost(@PathVariable Long id, @RequestBody Map<String, Object> updates);

    // DELETE
    @DeleteMapping("/api/posts/{id}")
    void deletePost(@PathVariable Long id);
}