package org.compass.desafio2;

import org.compass.desafio2.entity.Post;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = "/sql/posts-insert.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "/sql/posts-delete.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class PostIT {

    @Autowired
    private WebTestClient testClient;


    @Test
    public void buscarPost_ComIdExistente_RetornarPostComStatus200() {
        Post responseBody = testClient
                .get()
                .uri("/api/posts/1")
                .exchange()
                .expectStatus().isOk()
                .expectBody(Post.class)
                .returnResult().getResponseBody();

        assert responseBody != null;
        assert responseBody.getId() != null;
    }

    @Test
    public void criarPost_DadosValidos_RetornarPostsComStatus201() {
        Post postParaCriar = new Post(1L, 1L, "testando o post 1", "criando post 1");

        Post responseBody = testClient
                .post()
                .uri("/api/posts")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(postParaCriar)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(Post.class)
                .returnResult().getResponseBody();

        assert responseBody != null;
        assert responseBody.getId() != null;
        assert responseBody.getUserId().equals(postParaCriar.getUserId());
        assert responseBody.getTitle().equals(postParaCriar.getTitle());
        assert responseBody.getBody().equals(postParaCriar.getBody());
    }

    @Test
    public void editarPost_ComDadosValidos_RetornarStatus204() {
        Post postAtualizado = new Post(1L, 1L, "Título atualizado", "Corpo atualizado");

        testClient
                .put()
                .uri("/api/posts/1")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(postAtualizado)
                .exchange()
                .expectStatus().isNoContent();
    }


    @Test
    public void editarPost_ComDadosInvalidos_RetornarStatus400() {
        Post postInvalido = new Post(1L, 1L, "", "");

        testClient
                .put()
                .uri("/api/posts/1")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(postInvalido)
                .exchange()
                .expectStatus().isBadRequest();
    }

    @Test
    public void editarPost_PostInexistente_RetornarStatus404() {
        Post postAtualizado = new Post(999L, 1L, "Título atualizado", "Corpo atualizado"); // ID que não existe
        testClient
                .put()
                .uri("/api/posts/999") // ID inexistente
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(postAtualizado)
                .exchange()
                .expectStatus().isNotFound();
    }

}