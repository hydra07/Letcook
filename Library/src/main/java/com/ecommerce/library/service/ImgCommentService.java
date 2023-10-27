package com.ecommerce.library.service;

import com.ecommerce.library.model.ImgComment;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ImgCommentService {

    ImgComment save(ImgComment imgComment);
    List<ImgComment> findImgCommentsByComment_Id(Long commentId);

}
