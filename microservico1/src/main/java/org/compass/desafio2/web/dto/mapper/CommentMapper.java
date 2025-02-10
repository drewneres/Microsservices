package org.compass.desafio2.web.dto.mapper;

import org.compass.desafio2.model.Comment;
import org.compass.desafio2.web.dto.CommentDto;
import org.modelmapper.ModelMapper;

public class CommentMapper {

    public Comment toObject(CommentDto dto) {
        return new ModelMapper().map(dto, Comment.class);
    }

    public CommentDto toDto(Comment comment) {
        return new ModelMapper().map(comment, CommentDto.class);
    }
}
