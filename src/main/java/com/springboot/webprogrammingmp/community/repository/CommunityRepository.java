package com.springboot.webprogrammingmp.community.repository;

import com.springboot.webprogrammingmp.community.entity.Community;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface CommunityRepository extends JpaRepository<Community, Long> {

    Optional<Community> findById(Long id);
    List<Community> findByTitleContaining(String query);

}
