package org.compass.desafio2;

import org.compass.desafio2.controller.exception.ErrorMessage;
import org.compass.desafio2.entity.Comment;
import org.compass.desafio2.entity.Post;
import org.compass.desafio2.web.dto.PostDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = "/sql/comments-insert.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "/sql/comments-delete.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@Sql(scripts = "/sql/posts-insert.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "/sql/posts-delete.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class PostIT {

    @Autowired
    private WebTestClient testClient;

    // ------------------- GET Endpoints -------------------
    @Test
    public void getAllPosts_NoPosts_ReturnsEmptyListWithStatus200() {
        testClient
                .delete()
                .uri("/api/posts/1")
                .exchange()
                .expectStatus().isNoContent();

        List<Post> responseBody = testClient
                .get()
                .uri("/api/posts")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Post.class)
                .returnResult().getResponseBody();

        assert responseBody != null;
        assert responseBody.isEmpty();
    }

    @Test
    public void getAllPosts_WithExistingPosts_ReturnsListWithStatus200() {
        List<Post> responseBody = testClient
                .get()
                .uri("/api/posts")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Post.class)
                .returnResult().getResponseBody();

        assert responseBody != null;
        assert !responseBody.isEmpty();
    }

    @Test
    public void getPostById_WithExistingId_ReturnsPostWithStatus200() {
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
    public void getCommentsByPostId_WithExistingPost_ReturnsListWithStatus200() {
        List<Comment> responseBody = testClient
                .get()
                .uri("/api/posts/1/comments")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Comment.class)
                .returnResult().getResponseBody();

        assert responseBody != null;
        assert !responseBody.isEmpty();
    }

    @Test
    public void getCommentsByPostId_WithNonExistingPost_ReturnsStatus404() {
        testClient
                .get()
                .uri("/api/posts/999/comments")
                .exchange()
                .expectStatus().isNotFound();
    }

    // ------------------- DELETE Endpoints -------------------
    @Test
    public void deletePost_WithExistingId_ReturnsStatus204() {
        testClient
                .delete()
                .uri("/api/posts/1")
                .exchange()
                .expectStatus().isNoContent();
    }

    @Test
    public void deletePost_WithNonExistingId_ReturnsStatus404() {
        testClient
                .delete()
                .uri("/api/posts/999")
                .exchange()
                .expectStatus().isNotFound();
    }

    // ------------------- POST Endpoints -------------------
    @Test
    public void createPost_WithValidData_ReturnsPostWithStatus201() {
        Post newPost = new Post(1L, 1L, "Testing post 1", "Creating post 1");

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
    public void createPost_WithInvalidData_ReturnsStatus400() {
        Post invalidPost = new Post(null, null, "", "");

        testClient
                .post()
                .uri("/api/posts")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(invalidPost)
                .exchange()
                .expectStatus().isBadRequest();
    }

    // ------------------- PUT Endpoints -------------------
    @Test
    public void updatePost_WithValidData_ReturnsStatus204() {
        Post updatedPost = new Post(1L, 1L, "Updated Title", "Updated Body");

        testClient
                .put()
                .uri("/api/posts/1")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(updatedPost)
                .exchange()
                .expectStatus().isNoContent();
    }

    @Test
    public void updatePost_WithInvalidData_ReturnsStatus400() {
        Post invalidPost = new Post(1L, 1L, "", "");

        testClient
                .put()
                .uri("/api/posts/1")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(invalidPost)
                .exchange()
                .expectStatus().isBadRequest();
    }

    @Test
    public void updatePost_WithNonExistingId_ReturnsStatus404() {
        Post updatedPost = new Post(999L, 1L, "Updated Title", "Updated Body");
        testClient
                .put()
                .uri("/api/posts/999")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(updatedPost)
                .exchange()
                .expectStatus().isNotFound();
    }
}
