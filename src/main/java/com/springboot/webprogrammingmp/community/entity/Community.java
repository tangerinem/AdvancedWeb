package com.springboot.webprogrammingmp.community.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@NoArgsConstructor
@ToString
@Getter
@Setter
public class Community {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String date;

    private String title;

    private String comment;

    private String content;

    public Community(Long id, String date, String title, String comment, String content) {
        this.id = id;
        this.date = date;
        this.title = title;
        this.comment = comment;
        this.content = content;
    }


}
