package com.springboot.webprogrammingmp.login.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity @ToString @Getter @NoArgsConstructor
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    private String email;
    private String pw;

    public Member(Long id, String email, String pw){
        this.id = id;
        this.email = email;
        this.pw = pw;
    }
    public Member(String email, String pw){
        this.email = email;
        this.pw = pw;
    }

}
