package com.ecommerce.library.service;

import com.ecommerce.library.model.Reaction;
import net.minidev.json.JSONArray;
import org.springframework.stereotype.Service;

@Service
public interface ReactionService {
//    void save(Long userId, Long commentId, String reactionType);

    void save(Reaction reaction);

//    void delete(Long userId, Long commentId);

    void delete(Reaction reaction);

    int countReactionsByComment_IdAndType(Long commentId, String type);

    int countReactionsByComment_IdAndCustomer_IdAndType(Long commentId, Long customerId, String type);

    Reaction findReactionsByCommentIdAndCustomerId(Long commentId, Long customerId);

    void deleteByCommentIdAndCustomerId(Long commentId, Long customerId);

    JSONArray getReactionsByCommentId(Long commentId);

    boolean checkReaction(Long commentId, Long customerId, String type);

    void changeReaction(Long commentId, Long customerId, String type);

    boolean unReaction(Long commentId, Long customerId, String type);

}
