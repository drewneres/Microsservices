package org.compass.desafio2.servico;

import lombok.RequiredArgsConstructor;
import org.compass.desafio2.entidade.Comment;
import org.compass.desafio2.repository.CommentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CommentService {

    private final CommentRepository commentRepository;

    @Transactional(readOnly = true)
    public Comment getById(int id) {
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

    //Passar o Dto para atualizar só o body e name
    public Comment update(Comment comment) {
        return commentRepository.save(comment);
    }

    public void deleteById(int id) {
        commentRepository.deleteById(id);
    }
}
