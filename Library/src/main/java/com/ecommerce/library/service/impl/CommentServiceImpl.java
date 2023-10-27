package com.ecommerce.library.service.impl;


import com.ecommerce.library.model.Comment;
import com.ecommerce.library.repository.CommentRepository;
import com.ecommerce.library.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository commentRepository;
    @Override
    public void save(Comment comment) {
        commentRepository.save(comment);
    }

//    @Override
//    public Comment update(Comment comment) {
//        commentRepository.
//    }

    @Override
    public List<Comment> findAllCommentByRecipeId(Long recipeId) {
        return commentRepository.findAllCommentByRecipeId(recipeId);
    }

    @Override
    public Comment findById(Long id) {
        return commentRepository.findById(id).orElse(null);
    }

}
