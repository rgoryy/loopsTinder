package com.example.clothestinder.dao;

import com.example.clothestinder.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagDAO extends JpaRepository<Tag, Long> {
    Tag getTagByTagItself(String tagItself);
}
