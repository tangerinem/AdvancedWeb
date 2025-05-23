package com.springboot.webprogrammingmp.community.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
