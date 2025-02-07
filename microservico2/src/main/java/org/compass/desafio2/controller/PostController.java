package org.compass.desafio2.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.compass.desafio2.controller.exception.ErrorMessage;
import org.compass.desafio2.entity.Comment;
import org.compass.desafio2.entity.Post;
import org.compass.desafio2.service.PostService;
import org.compass.desafio2.web.dto.PostDto;
import org.compass.desafio2.web.dto.mapper.PostMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    private final PostService postService;
    private final PostMapper postMapper;

    @Autowired
    public PostController(PostService postService, PostMapper postMapper) {
        this.postService = postService;
        this.postMapper = postMapper;
    }

    @Operation(summary = "Listar todos os posts cadastrados", description = "Recurso para listar todos os posts cadastrados",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Lista com todos os posts cadastrados",
                            content = @Content(mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = PostDto.class))))
            })
    @GetMapping
    public ResponseEntity<List<PostDto>> getAllPosts() {
        List<PostDto> posts = postService.getAllPosts().stream()
                .map(postMapper::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(posts);
    }

    @Operation(summary = "Recuperar um post pelo id", description = "Recurso para recuperar um post pelo id",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Recurso recuperado com sucesso",
                            content = @Content(mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = PostDto.class)))),
                    @ApiResponse(responseCode = "400", description = "Requisição inválida",
                            content = @Content(mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = ErrorMessage.class)))),
                    @ApiResponse(responseCode = "404", description = "Recurso não encontrado",
                            content = @Content(mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = ErrorMessage.class))))
            })
    @GetMapping("/{id}")
    public Post getPostById(@PathVariable Long id) {
        return postService.getPostById(id);
    }

    @GetMapping("/{id}/comments")
    public ResponseEntity<List<Comment>> getCommentsByPostId(@PathVariable Long id) {
        List<Comment> comments = postService.getAllCommentsByPostId(id);
        return ResponseEntity.ok(comments);
    }

    @PostMapping
    public ResponseEntity<PostDto> createPost(@Valid @RequestBody PostDto postDto) {
        Post post = postMapper.toEntity(postDto);
        Post savedPost = postService.savePost(post);
        return ResponseEntity.status(HttpStatus.CREATED).body(postMapper.toDto(savedPost));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updatePost(@PathVariable Long id, @Valid @RequestBody PostDto postDto) {
        Post updatedPost = postService.updatePost(id, postMapper.toEntity(postDto));
        if (updatedPost != null) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable Long id) {
        postService.deletePost(id);
        return ResponseEntity.noContent().build();
    }
}