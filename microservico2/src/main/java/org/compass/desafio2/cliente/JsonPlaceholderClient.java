package org.compass.desafio2.cliente;

import org.compass.desafio2.entidade.Post;
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
}
