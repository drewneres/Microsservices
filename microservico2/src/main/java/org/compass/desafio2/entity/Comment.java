package org.compass.desafio2.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Entity
@Table(name = "tb_comments")
public class Comment {
    @Id
    private Long id;

    @Column(name = "post_id")
    private Long postId;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(length = 1000)
    private String body;

    public Comment() {}
}