package org.compass.desafio2;

import org.compass.desafio2.controller.exception.ErrorMessage;
import org.compass.desafio2.model.Post;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
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

    @Test
    public void getPost_WithInvalidId_ReturnErrorMessageWithStatus404() {
        ErrorMessage responseBody = testClient
                .get()
                .uri("/api/posts/0")
                .exchange()
                .expectStatus().isNotFound()
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(404);
    }



    @Test
    public void createPost_ValidData_ReturnPostsWithStatus201() {
        Post newPost = new Post(101L, "test create post 101", "test creating post 101",1L);

        Post responseBody = testClient
                .post()
                .uri("/api/posts")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(newPost)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(Post.class)
                .returnResult().getResponseBody();

        assert responseBody != null;
        assert responseBody.getId() != null;
        assert responseBody.getUserId().equals(newPost.getUserId());
        assert responseBody.getTitle().equals(newPost.getTitle());
        assert responseBody.getBody().equals(newPost.getBody());
    }

    @Test
    public void createPost_WithInvalidData_ReturnErrorMessageWithStatus422() {
        ErrorMessage responseBody = testClient
                .post()
                .uri("/api/posts")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new Post(1L, "", "", 100L ))
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(404);
    }

    @Test
    public void createPost_IdAlreadyExists_ReturnErrorMessageWithStatus409() {
        Post newPost = new Post(1L, "test create post 2", "test creating post 2",1L);

        ErrorMessage responseBody = testClient
                .post()
                .uri("/api/posts")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(newPost).exchange()
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
                .uri("/api/posts/1")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new Post(1L, "Updated title test", "Updated body test", 1L ))
                .exchange()
                .expectStatus().isNoContent();
    }

    @Test
    public void updatePost_WithInvalidId_ReturnErrorMessageWithStatus404() {
        ErrorMessage responseBody = testClient
                .put()
                .uri("/api/posts/0")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new Post(0L, "Updated title test", "Updated body test", 1L ))
                .exchange()
                .expectStatus().isNotFound()
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(404);
    }

    @Test
    public void updatePost_WithInvalidData_ReturnErrorMessageWithStatus422() {
        ErrorMessage responseBody = testClient
                .put()
                .uri("/api/posts/1")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new Post(1L, "", "", 100L ))
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(404);
    }

    @Test
    public void deletePost_ValidData_ReturnLocationStatus204() {
        testClient
                .delete()
                .uri("/api/posts/1")
                .exchange().expectStatus()
                .isNoContent();
    }

    @Test
    public void deletePost_WithInvalidId_ReturnErrorMessageWithStatus404() {
        ErrorMessage responseBody = testClient
                .delete()
                .uri("/api/posts/0")
                .exchange()
                .expectStatus().isNotFound()
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(404);
    }

}
