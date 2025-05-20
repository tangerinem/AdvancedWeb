package com.springboot.webprogrammingmp.login.Repository;

import com.springboot.webprogrammingmp.login.Entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> { // extends 변경
    Optional<Member> findByEmail(String email);

    @Override
    List<Member> findAll();
}