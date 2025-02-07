package org.compass.desafio2.web.dto.mapper;

import org.compass.desafio2.entity.Post;
import org.compass.desafio2.web.dto.PostDto;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class PostMapper {

    private final ModelMapper modelMapper;

    public PostMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public Post toEntity(PostDto dto) {
        return modelMapper.map(dto, Post.class);
    }

    public PostDto toDto(Post post) {
        return modelMapper.map(post, PostDto.class);
    }
}
