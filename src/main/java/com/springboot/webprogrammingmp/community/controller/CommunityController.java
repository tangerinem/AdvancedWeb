package com.springboot.webprogrammingmp.community.controller;

import com.springboot.webprogrammingmp.community.dto.CommunityForm;
import com.springboot.webprogrammingmp.community.entity.Community;
import com.springboot.webprogrammingmp.community.repository.CommunityRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class CommunityController {

    private final CommunityRepository communityRepository;
    public CommunityController(CommunityRepository communityRepository){
        this.communityRepository = communityRepository;
    }


    /* 커뮤니티 전체 글 보기 */
    @GetMapping("/community")
    public String viewAll(Model model){
        List<Community> communityList = communityRepository.findAll();
        model.addAttribute("community", communityList);
        return "community/index";
    }

    /* 커뮤니티 새 글 생성*/
    @GetMapping("/community/new")
    public String createNewPost() {
        return "community/new";
    }
    @PostMapping("/community/create")
    public String newPost(@ModelAttribute CommunityForm form) {
        Community community = form.toEntity();
        communityRepository.save(community);
        return "redirect:/community";
    }




    /* 커뮤니티 글 수정 페이지 */
    @GetMapping("/community/edit/{id}")
    public String editPost(@PathVariable("id")Long id, Model model) {
        Community community = communityRepository.findById(id).orElse(null);
        model.addAttribute("community", community);
        return "community/edit";
    }

    /* 글 수정 */
    @PostMapping("/community/edit/{id}")
    public String updatePost(@PathVariable("id") Long id, @ModelAttribute Community updatedCommunity){
        Community target = communityRepository.findById(id).orElse(null);

        target.setTitle(updatedCommunity.getTitle());
        target.setContent(updatedCommunity.getContent());

        communityRepository.save(target);
        return "redirect:/community/" + id;
    }


    /* 글 삭제 */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Community> deletePost(@PathVariable Long id){
        Community target = communityRepository.findById(id).orElse(null);
        if (target == null) {
            return ResponseEntity.notFound().build();
        }
        communityRepository.deleteById(id);
        return ResponseEntity.ok(null);
    }

    /* 글 상세 보기 */
    @GetMapping("/community/{id}")
    public String show(@PathVariable("id")Long id, Model model) {
        Community communityEntity = communityRepository.findById(id).orElse(null);
        model.addAttribute("community", communityEntity);

        return "community/show";
    }

}
