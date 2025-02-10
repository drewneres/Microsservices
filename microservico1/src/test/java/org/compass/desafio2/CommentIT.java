package org.compass.desafio2;

import org.compass.desafio2.controller.exception.ErrorMessage;
import org.compass.desafio2.model.Comment;
import org.compass.desafio2.model.Post;
import org.compass.desafio2.web.dto.CommentDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CommentIT {

    @Autowired
    private WebTestClient testClient;

    @Test
    public void createComment_ValidData_ReturnPostsWithStatus201() {
        Comment newComment = new Comment(10L, "test create comment 10","test@gmail.com", "test creating comment 10 in post 1",1L);

        Post responseBody = testClient
                .post()
                .uri("/api/posts/1/comments")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(newComment)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(Post.class)
                .returnResult().getResponseBody();

        assert responseBody != null;
        assert responseBody.getId() != null;
        assert responseBody.getBody().equals(newComment.getBody());
    }

    @Test
    public void createComment_WithInvalidData_ReturnErrorMessageWithStatus422() {
        ErrorMessage responseBody = testClient
                .post()
                .uri("/api/posts/1/comments")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new Comment(10L, "test create comment 10","test@gmail.com", "test creating comment 10 in post 1",1L))
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(404);
    }

    @Test
    public void createComment_IdAlreadyExists_ReturnErrorMessageWithStatus409() {
        Comment newComment = new Comment(1L, "test create comment 10","test@gmail.com", "test creating comment 10 in post 1",1L);

        ErrorMessage responseBody = testClient
                .post()
                .uri("/api/posts/1/comments")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(newComment).exchange()
                .expectStatus().isEqualTo(409)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        assert responseBody != null;
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(409);
    }

    @Test
    public void updatePost_ValidData_ReturnStatus204() {

        testClient
                .put()
                .uri("/api/posts/1/comments/1")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new CommentDto("test create comment 10","test@gmail.com"))
                .exchange()
                .expectStatus().isNoContent();
    }

    @Test
    public void updateComment_WithInvalidId_ReturnErrorMessageWithStatus404() {
        ErrorMessage responseBody = testClient
                .put()
                .uri("/api/posts/1/comments/100")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new CommentDto("test create comment 10","test@gmail.com"))
                .exchange()
                .expectStatus().isNotFound()
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(404);
    }

    @Test
    public void deleteComment_ValidData_ReturnLocationStatus204() {
        testClient
                .delete()
                .uri("/api/posts/1/comments/1")
                .exchange().expectStatus()
                .isNoContent();
    }

    @Test
    public void deletePost_WithInvalidId_ReturnErrorMessageWithStatus404() {
        ErrorMessage responseBody = testClient
                .delete()
                .uri("/api/posts/1/comments/100")
                .exchange()
                .expectStatus().isNotFound()
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(404);
    }


}
