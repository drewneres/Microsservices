package org.compass.desafio2.client;

import org.compass.desafio2.model.Post;
import org.compass.desafio2.model.Comment;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "microsservico-b", url = "${microsservico-b.url}")
public interface MicrosservicoBClient {

    @GetMapping("/api/posts")
    List<Post> getPosts();

    @GetMapping("/api/posts/{id}")
    Post getPostById(@PathVariable Long id);

    @GetMapping("/api/posts/{id}/comments")
    List<Comment> getCommentsByPostId(@PathVariable Long id);
}