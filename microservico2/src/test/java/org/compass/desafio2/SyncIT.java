package org.compass.desafio2;

import org.compass.desafio2.service.SyncService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SyncIT {

    @Autowired
    private WebTestClient testClient;

    @Autowired
    private SyncService syncService;

    @Test
    public void syncPostsExternalApiDataReturnStatus200() {
        testClient
                .post()
                .uri("/api/sync-data/posts")
                .contentType(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class)
                .isEqualTo("Posts synchronized successfully!");
    }

    @Test
    public void syncCommentsExternalApiDataReturnStatus200() {
        testClient
                .post()
                .uri("/api/sync-data/comments")
                .contentType(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class)
                .isEqualTo("Comments synchronized successfully!");
    }

    @Test
    public void syncUsersExternalApiDataReturnStatus200() {
        testClient
                .post()
                .uri("/api/sync-data/users")
                .contentType(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class)
                .isEqualTo("Users synchronized successfully!");
    }
}
