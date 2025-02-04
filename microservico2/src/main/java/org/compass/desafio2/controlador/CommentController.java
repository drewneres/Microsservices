package org.compass.desafio2.controlador;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.compass.desafio2.entidade.Comment;
import org.compass.desafio2.servico.CommentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/posts/{id}/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @GetMapping("/{id}")
    public ResponseEntity<Comment> getById(Integer id) {
        Comment comment = commentService.getById(id);
        return ResponseEntity.ok(comment);
    }

    @GetMapping
    public ResponseEntity<List<Comment>> getAll() {
        List<Comment> comments = commentService.getAll();
        return ResponseEntity.ok(comments);
    }

    @PostMapping
    public ResponseEntity<Comment> create(@RequestBody @Valid Comment comment) {
        return ResponseEntity.ok(commentService.create(comment));
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteById(@PathVariable Integer id) {
        try{
            commentService.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        catch(Exception e) {
            throw new RuntimeException("Post de Id nao encontrado");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Comment> update(@PathVariable Integer id, @RequestBody @Valid Comment comment) {
        Comment oldComment = commentService.getById(id);
        comment.setId(oldComment.getId());
        commentService.update(comment);
        return ResponseEntity.ok(comment);
    }

}
