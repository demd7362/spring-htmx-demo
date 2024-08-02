package com.api.sample.entity.board;

import com.api.sample.entity.common.CommonProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "post")
@SuperBuilder
@Getter
@NoArgsConstructor
public class Post extends CommonProperties {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, columnDefinition = "varchar(50)")
    private String title;
    @Column(nullable = false, columnDefinition = "text")
    private String content;
    @Column(nullable = false, columnDefinition = "varchar(50)")
    private String author;
    @Column(nullable = false, columnDefinition = "varchar(20)")
    private String category;
}
