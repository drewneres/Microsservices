package org.compass.desafio2;

import org.compass.desafio2.entity.Comment;
import org.compass.desafio2.entity.Post;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = "/sql/comments-insert.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "/sql/comments-delete.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@Sql(scripts = "/sql/posts-insert.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "/sql/posts-delete.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)

public class CommentIT {

    @Autowired
    private WebTestClient testClient;


    @Test
    public void criarComments_DadosValidos_RetornarPostsComStatus200() {
        Comment commentParaCriar = new Comment(1L, 1L, "joão", "joao@email.com", "criando comentário");

        Comment responseBody = testClient
                .post()
                .uri("/posts/1/comments")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(commentParaCriar)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Comment.class)
                .returnResult().getResponseBody();

        assert responseBody != null;
        assert responseBody.getId() != null;
        assert responseBody.getPostId().equals(commentParaCriar.getPostId());
        assert responseBody.getName().equals(commentParaCriar.getName());
        assert responseBody.getEmail().equals(commentParaCriar.getEmail());
        assert responseBody.getBody().equals(commentParaCriar.getBody());
    }

    @Test
    public void editarComment_ComDadosValidos() {
        Comment commentAtualizado = new Comment(1L, 1L, "joão", "joao@email.com", "criando comentário");

        testClient
                .put()
                .uri("/posts/1/comments/1")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(commentAtualizado)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    public void editarPost_PostInexistente_RetornarStatus404() {
        Comment commentAtualizado = new Comment(999L, 1L, "Título", "teste@email.com", "Corpo atualizado");
        testClient
                .put()
                .uri("/posts/1/comments/999") // ID inexistente
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(commentAtualizado)
                .exchange()
                .expectStatus().isNotFound();
    }

}