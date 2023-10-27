package com.ecommerce.library.service.impl;

import com.ecommerce.library.model.ImgComment;
import com.ecommerce.library.repository.ImgCommentReponsitory;
import com.ecommerce.library.service.ImgCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ImgCommentServiceImpl implements ImgCommentService {

    @Autowired
    private ImgCommentReponsitory imgCommentReponsitory;
    @Override
    public ImgComment save(ImgComment imgComment) {
        return imgCommentReponsitory.save(imgComment);
    }

    @Override
    public List<ImgComment> findImgCommentsByComment_Id(Long commentId) {
        return imgCommentReponsitory.findImgCommentsByComment_Id(commentId);
    }
}
