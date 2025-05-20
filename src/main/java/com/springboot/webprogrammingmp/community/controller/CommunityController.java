package com.springboot.webprogrammingmp.community.controller;

import com.springboot.webprogrammingmp.community.dto.CommunityForm;
import com.springboot.webprogrammingmp.community.entity.Community;
import com.springboot.webprogrammingmp.community.repository.CommunityRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class CommunityController {

    private final CommunityRepository communityRepository;
    public CommunityController(CommunityRepository communityRepository){
        this.communityRepository = communityRepository;
    }


    /* 커뮤니티 전체 글 보기 */
    @GetMapping("/community")
    public String viewAll(Model model){
        List<Community> communityList = communityRepository.findAll();
        model.addAttribute("contents", communityList);
        return "/community/view";
    }

    /* 커뮤니티 새 글 생성*/
    @PostMapping("/community/create")
    public Community newPost(@RequestBody CommunityForm communityForm){
        Community community = communityForm.toEntity();
        Community saved = communityRepository.save(community);
        return saved;
    }

    /* 커뮤니티 글 수정 페이지 */
    @GetMapping("/community/edit/{id}/")
    public String editPost(@PathVariable("id")Long id, Model model) {
        Community community = communityRepository.findById(id).orElse(null);
        model.addAttribute("community", community);
        return "community/edit";
    }

    /* 글 수정 */
    @PutMapping("/community/edit/{id}")
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
            return ResponseEntity.notFound().build();  // 404
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
