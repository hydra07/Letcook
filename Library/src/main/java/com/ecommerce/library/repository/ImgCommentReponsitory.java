package com.ecommerce.library.repository;

import com.ecommerce.library.model.ImgComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImgCommentReponsitory extends JpaRepository<ImgComment, Long> {

    List<ImgComment> findImgCommentsByComment_Id(Long commentId);



}
