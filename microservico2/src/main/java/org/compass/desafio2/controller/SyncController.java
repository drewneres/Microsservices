package org.compass.desafio2.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.compass.desafio2.service.SyncService;
import org.springframework.http.ResponseEntity;
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

    @PostMapping
    public ResponseEntity<String> syncAll() {
        try {
            syncService.syncEverything();
            return ResponseEntity.ok("All data synchronized successfully!");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error synchronizing data: " + e.getMessage());
        }
    }

    @Operation(summary = "Sincronizar dados de Posts da API externa", description = "Recurso para sincronizar dados de Posts da API externa",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Posts sincronizados com sucesso")
            })
    @PostMapping("/posts")
    public String syncPosts() {
        syncService.syncPosts();
        return "Posts synchronized successfully!";
    }

    @Operation(summary = "Sincronizar dados de Comments da API externa", description = "Recurso para sincronizar dados de Comments da API externa",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Comentários sincronizados com sucesso")
            })
    @PostMapping("/comments")
    public String syncComments() {
        syncService.syncComments();
        return "Comments synchronized successfully!";
    }

    @Operation(summary = "Sincronizar dados de Users da API externa", description = "Recurso para sincronizar dados de User da API externa",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Usuários sincronizados com sucesso")
            })
    @PostMapping("/users")
    public String syncUsers() {
        syncService.syncUsers();
        return "Users synchronized successfully!";
    }
}