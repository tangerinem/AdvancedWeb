package com.springboot.webprogrammingmp.community.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@NoArgsConstructor
@ToString
@Getter
@Setter
public class Community {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate date;

    private String title;

    private String comment;

    /* ALTER TABLE community MODIFY COLUMN content TEXT; db에 명시 필수 */
    @Lob
    @Column(name = "content", columnDefinition = "TEXT")
    private String content;

    private String query;


    public Community(Long id, LocalDate date, String title, String comment, String content, String query) {
        this.id = id;
        this.date = date;
        this.title = title;
        this.comment = comment;
        this.query = query;
        this.content = content;
    }


}
