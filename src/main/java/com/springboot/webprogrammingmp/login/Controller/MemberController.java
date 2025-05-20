package com.springboot.webprogrammingmp.login.Controller;

import com.springboot.webprogrammingmp.login.dto.MemberForm;
import com.springboot.webprogrammingmp.login.Entity.Member;
import com.springboot.webprogrammingmp.login.Repository.MemberRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;
@Slf4j
@Controller
public class MemberController {
    @Autowired
    MemberRepository memberRepository;

    @GetMapping("/refri/signup")
    public String joinMember() {
        return "signup";
    }

    @PostMapping("/refri/save")
    public String saveMember(MemberForm memberForm) {
        Member member = memberForm.toEntity();
        memberRepository.save(member);
        return "redirect:/refri/login";
    }

    @GetMapping("/refri/login")
    public String login() {
        return "login";
    }

    @PostMapping("/refri/join")
    public String loginProcess(@RequestParam("email") String email, @RequestParam("password") String password, RedirectAttributes rttr) {
        Optional<Member> optionalMember = memberRepository.findByEmail(email);

        if (optionalMember.isPresent()) {
            Member member = optionalMember.get();
            if (member.getPw().equals(password)) {
                return "main";
            } else {
                rttr.addFlashAttribute("loginError", "비밀번호가 일치하지 않습니다.");

                return "redirect:/refri/login";
            }
        } else {
            rttr.addFlashAttribute("loginError", "존재하지 않는 이메일입니다.");
            return "redirect:/refri/login";
        }
    }
}