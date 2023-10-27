package com.ecommerce.library.service;

import com.ecommerce.library.model.Comment;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CommentService {
    void save(Comment comment);

//    Comment update(Comment comment);

    List<Comment> findAllCommentByRecipeId(Long recipeId);

    Comment findById(Long id);

}
