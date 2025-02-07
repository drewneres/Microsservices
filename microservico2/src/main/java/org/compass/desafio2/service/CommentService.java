package org.compass.desafio2.service;

import lombok.RequiredArgsConstructor;
import org.compass.desafio2.client.JsonPlaceholderClient;
import org.compass.desafio2.entity.Comment;
import org.compass.desafio2.exception.NotFoundException;
import org.compass.desafio2.exception.UniqueViolationException;
import org.compass.desafio2.repository.CommentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CommentService {

    private final CommentRepository commentRepository;

    private final JsonPlaceholderClient jsonPlaceholderClient;

    @Transactional
    public void syncData() {
        List<Comment> comments = jsonPlaceholderClient.getAllComments();
        commentRepository.saveAll(comments);
    }

    @Transactional(readOnly = true)
    public Comment getById(Long id) {
        return commentRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Comment id not found")
        );
    }

    @Transactional(readOnly = true)
    public List<Comment> getAll() {
        return commentRepository.findAll();
    }

    public Comment create(Comment comment) {
        try {
            return commentRepository.save(comment);
        } catch (RuntimeException e) {
            throw new UniqueViolationException("Comment Id already exists");
        }
    }

    public void deleteById(Long id) {
        try {
            commentRepository.deleteById(id);
        } catch (RuntimeException e) {
            throw new NotFoundException("Comment not found");
        }
    }

    public Comment updateComment(Long id, String name, String body) {
        try {
            getById(id).setName(name);
            getById(id).setBody(body);
            return commentRepository.save(getById(id));
        } catch (RuntimeException e) {
            throw new NotFoundException("Comment not found");
        }
    }

}
