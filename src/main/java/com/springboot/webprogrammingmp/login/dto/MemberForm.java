package com.springboot.webprogrammingmp.login.dto;


import com.springboot.webprogrammingmp.login.Entity.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@NoArgsConstructor
@ToString
public class MemberForm {
    private Long id;
    private String email;
    private String password;

    public MemberForm(Long id, String email, String password){
        this.id = id;
        this.email = email;
        this.password = password;
    }

    public Member toEntity(){
        return new Member(id, email, password);
    }
    public Member toEntityForSignup(){
        return new Member(email, password);
    }
}
