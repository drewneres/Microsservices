package org.compass.desafio2.client;

import org.compass.desafio2.entity.Comment;
import org.compass.desafio2.entity.Post;
import org.compass.desafio2.entity.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "jsonPlaceholderClient", url = "https://jsonplaceholder.typicode.com")
public interface JsonPlaceholderClient {

    @GetMapping("/posts")
    List<Post> getAllPosts();

    @GetMapping("/posts/{id}")
    Post getPostById(@PathVariable("id") Long id);

    @GetMapping("/posts/{id}/comments")
    List<Comment> getCommentsByPostId(@PathVariable("id") Long id);

    @GetMapping("/comments")
    List<Comment> getAllComments();

    @GetMapping("/users")
    List<User> getAllUsers();

    @GetMapping("/users/{id}")
    User getUserById(@PathVariable("id") Long id);
}
