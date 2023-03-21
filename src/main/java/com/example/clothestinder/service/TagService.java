package com.example.clothestinder.service;

import com.example.clothestinder.TagAlreadyExistsException;
import com.example.clothestinder.dao.TagDAO;
import com.example.clothestinder.entity.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TagService {
    private final TagDAO tagDAO;

    public TagService(TagDAO tagDAO) {
        this.tagDAO = tagDAO;
    }

    public Optional<Tag> getTagByName(String tagByItself) {
        Tag tag = tagDAO.getTagByTagItself(tagByItself);
        if (tag == null) {
            return Optional.empty();
        } else {
            return Optional.of(tag);
        }
    }

    public void addNewTag(String tagToAdd) {
        Optional<Tag> optionalTag = getTagByName(tagToAdd);
        if (!optionalTag.isEmpty())
            throw new TagAlreadyExistsException();
        Tag tag = new Tag();
        tag.setTagItself(tagToAdd);
        tagDAO.save(tag);
    }
}
