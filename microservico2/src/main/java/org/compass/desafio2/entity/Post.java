package org.compass.desafio2.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@Table(name = "tb_post")
public class Post {
    @Id
    private Long id;
    private Long userId;
    private String title;
    private String body;

    public Post() {}
}
