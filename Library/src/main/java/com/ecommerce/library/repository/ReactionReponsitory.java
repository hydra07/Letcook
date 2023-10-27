package com.ecommerce.library.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.ecommerce.library.model.Reaction;

import java.util.List;

@Repository
public interface ReactionReponsitory extends JpaRepository<Reaction, Long> {

    @Query("select r from Reaction r where r.comment.id = ?1")
    List<Reaction> findReactionsByComment_Id(Long commentId);

//    @Query("select r from Reaction r where r.comment.id = ?1 and r.customers.id = ?2")
//    List<Reaction> findReactionsByComment_IdAndCustomer_Id(Long commentId, Long customerId);

    @Query("select r from Reaction r join r.customers c where r.comment.id = ?1 and c.id = ?2")
    Reaction findReactionsByCommentIdAndCustomerId(Long commentId, Long customerId);

    @Query("select count(r) from Reaction r where r.comment.id = ?1 and r.type = ?2")
    int countReactionsByComment_IdAndType(Long commentId, String type);

    //check if customer has reacted to comment
    @Query("select count(r) from Reaction r join r.customers c where r.comment.id = ?1 and c.id = ?2 and r.type = ?3")
    int countReactionsByComment_IdAndCustomer_IdAndType(Long commentId, Long customerId, String type);

}
