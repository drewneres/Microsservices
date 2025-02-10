package org.compass.desafio2.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.compass.desafio2.controller.exception.ErrorMessage;
import org.compass.desafio2.service.SyncService;
import org.compass.desafio2.web.dto.PostDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/sync-data")
public class SyncController {

    private final SyncService syncService;

    public SyncController(SyncService syncService) {
        this.syncService = syncService;
    }

    @Operation(summary = "Sincronizar todos os dados da API externa", description = "Recurso para sincronizar todos od dados da API externa",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Dados sincronizados com sucesso"),
                    @ApiResponse(responseCode = "500", description = "Erro interno ao sincronizar dados",
                            content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorMessage.class)))
            })
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
                    @ApiResponse(responseCode = "200", description = "Posts sincronizados com sucesso"),
                    @ApiResponse(responseCode = "500", description = "Erro interno ao sincronizar dados",
                            content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorMessage.class)))
            })
    @PostMapping("/posts")
    public String syncPosts() {
        syncService.syncPosts();
        return "Posts synchronized successfully!";
    }

    @Operation(summary = "Sincronizar dados de Comments da API externa", description = "Recurso para sincronizar dados de Comments da API externa",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Comentários sincronizados com sucesso"),
                    @ApiResponse(responseCode = "500", description = "Erro interno ao sincronizar dados",
                            content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorMessage.class)))
            })
    @PostMapping("/comments")
    public String syncComments() {
        syncService.syncComments();
        return "Comments synchronized successfully!";
    }

    @Operation(summary = "Sincronizar dados de Users da API externa", description = "Recurso para sincronizar dados de User da API externa",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Usuários sincronizados com sucesso"),
                    @ApiResponse(responseCode = "500", description = "Erro interno ao sincronizar dados",
                            content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorMessage.class)))
            })
    @PostMapping("/users")
    public String syncUsers() {
        syncService.syncUsers();
        return "Users synchronized successfully!";
    }
}