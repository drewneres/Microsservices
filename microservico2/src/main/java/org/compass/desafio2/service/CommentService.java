package org.compass.desafio2.service;

import lombok.RequiredArgsConstructor;
import org.compass.desafio2.client.JsonPlaceholderClient;
import org.compass.desafio2.entity.Comment;
import org.compass.desafio2.entity.Post;
import org.compass.desafio2.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
                () -> new RuntimeException("Comment id not found")
        );
    }

    @Transactional(readOnly = true)
    public List<Comment> getAll() {
        return commentRepository.findAll();
    }

    public Comment create(Comment comment) {
        try{
            return commentRepository.save(comment);
        }
        catch (Exception e){
            throw new RuntimeException("Id do cometario ja existente");
        }
    }

    public void deleteById(Long id) {
        commentRepository.deleteById(id);
    }

    public Comment updateComment(Long id, String name, String body) {
        getById(id).setName(name);
        getById(id).setBody(body);
        return commentRepository.save(getById(id));
    }

}
