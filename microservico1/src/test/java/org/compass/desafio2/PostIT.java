package org.compass.desafio2;

import org.compass.desafio2.model.Post;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
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
    public void getPost_ValidId_ReturnPostWithStatus200() {
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

    //Criar teste para buscar com id inexistente, após ser feito o tratamento de exceção



    @Test
    public void createPost_ValidData_ReturnLocationStatus201() {
        testClient
                .post()
                .uri("/api/posts")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new Post(2L, "test create post 2", "test creating post 2",2L ))
                .exchange()
                .expectStatus().isCreated()
                .expectHeader().exists(HttpHeaders.LOCATION);
    }

    ////Criar teste para criar com id inválido, após ser feito o tratamento de exceção

    //Criar teste para criar com id existente, após ser feito o tratamento de exceção

    @Test
    public void updatePost_ValidData_ReturnStatus204() {

        testClient
                .put()
                .uri("/api/posts/1")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new Post(1L, "Updated title test", "Updated body test", 1L ))
                .exchange()
                .expectStatus().isNoContent();
    }

    //Criar teste para atualizar Post inexistente, após ser feito o tratamento de exceção

    //Criar teste para atualizar Post com dados inválidos, após ser feito o tratamento de exceção

    @Test
    public void deletePost_ValidData_ReturnLocationStatus204() {
        testClient
                .delete()
                .uri("/api/posts/1")
                .exchange().expectStatus()
                .isNoContent();
    }

    //Criar teste para deletar Post inexistente, após ser feito o tratamento de exceção

}
