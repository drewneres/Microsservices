package org.compass.desafio2.controller;

import org.compass.desafio2.service.SyncService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/sync-data") // Mapeia o endpoint base
public class SyncController {

    private final SyncService syncService;

    public SyncController(SyncService syncService) {
        this.syncService = syncService;
    }

    @PostMapping("/posts")
    public String syncPosts() {
        syncService.syncPosts();
        return "Posts synchronized successfully!";
    }

    @PostMapping("/comments")
    public String syncComments() {
        syncService.syncComments();
        return "Comments synchronized successfully!";
    }

    @PostMapping("/users")
    public String syncUsers() {
        syncService.syncUsers();
        return "Users synchronized successfully!";
    }
}