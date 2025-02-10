package org.compass.desafio2.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.compass.desafio2.client.JsonPlaceholderClient;
import org.compass.desafio2.controller.exception.ErrorMessage;
import org.compass.desafio2.entity.Comment;
import org.compass.desafio2.service.CommentService;
import org.compass.desafio2.web.dto.CommentDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/posts/{postId}/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    private final JsonPlaceholderClient jsonPlaceholderClient;

    @Operation(summary = "Recuperar um comentário pelo ID",
            description = "Recurso para recuperar um comentário específico",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Comentário recuperado com sucesso",
                            content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Comment.class))),
                    @ApiResponse(responseCode = "404", description = "Comentário não encontrado",
                            content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorMessage.class)))
            })
    @GetMapping("/{id}")
    public ResponseEntity<Comment> getById(Long id) {
        Comment comment = commentService.getById(id);
        return ResponseEntity.ok(comment);
    }

    @Operation(summary = "Listar todos os comentários",
            description = "Recurso para listar todos os comentários cadastrados",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Lista de comentários",
                            content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = Comment.class))))
            })
    @GetMapping
    public ResponseEntity<List<Comment>> getAll() {
        List<Comment> comments = commentService.getAll();
        return ResponseEntity.ok(comments);
    }

    @Operation(summary = "Criar um novo comentário",
            description = "Recurso para criar um novo comentário vinculado a um post",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Comentário criado com sucesso",
                            content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Comment.class))),
                    @ApiResponse(responseCode = "400", description = "Dados inválidos",
                            content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "404", description = "Post não encontrado",
                            content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "422", description = "Campo(s) inválido(s)",
                            content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorMessage.class)))
            })
    @PostMapping
    public ResponseEntity<Comment> create(@RequestBody @Valid Comment comment) {
        return ResponseEntity.ok(commentService.create(comment));
    }

    @Operation(summary = "Excluir um comentário",
            description = "Exclui um comentário específico pelo seu ID",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Comentário excluído com sucesso"),
                    @ApiResponse(responseCode = "404", description = "Comentário não encontrado",
                            content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorMessage.class))
                    )
            }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        commentService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Atualizar um comentário",
            description = "Atualiza os dados de um comentário existente",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Comentário atualizado com sucesso",
                            content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Comment.class))
                    ),
                    @ApiResponse(responseCode = "400",
                            description = "Dados inválidos",
                            content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorMessage.class))
                    ),
                    @ApiResponse(responseCode = "404", description = "Comentário não encontrado",
                            content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorMessage.class))
                    ),
                    @ApiResponse(responseCode = "422", description = "Campos inválidos no corpo da requisição",
                            content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorMessage.class))
                    )
            }
    )
    @PutMapping("/{id}")
    public ResponseEntity<Comment> update(@PathVariable Long id, @RequestBody @Valid CommentDto commentDto) {
        Comment comment = commentService.updateComment(id, commentDto.getName(), commentDto.getBody());
        return ResponseEntity.ok(comment);
    }
}
