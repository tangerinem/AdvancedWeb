package com.springboot.webprogrammingmp.community.dto;

import com.springboot.webprogrammingmp.community.entity.Community;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CommunityForm {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate date;
    private String title;
    private String comment;
    private String content;
    private String query;

    public Community toEntity(){
        return new Community(null, this.date, this.title, this.comment, this.content, this.query);
    }

}
