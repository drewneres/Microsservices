package org.compass.desafio2.servico;

import org.compass.desafio2.cliente.JsonPlaceholderClient;
import org.compass.desafio2.entidade.Post;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostServico {

    private final JsonPlaceholderClient jsonPlaceholderClient;


    public PostServico(JsonPlaceholderClient jsonPlaceholderClient) {
        this.jsonPlaceholderClient = jsonPlaceholderClient;
    }

    public List<Post> getAllPosts() {
        return jsonPlaceholderClient.getAllPosts();
    }

    public Post getPostById(Long id) {
        return jsonPlaceholderClient.getPostById(id);
    }
}
