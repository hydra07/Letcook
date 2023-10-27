package com.ecommerce.library.service.impl;

import com.ecommerce.library.model.Reaction;
import com.ecommerce.library.repository.ReactionReponsitory;
import com.ecommerce.library.service.ReactionService;
import net.minidev.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReactionServiceImpl implements ReactionService {

    @Autowired
    private ReactionReponsitory reactionReponsitory;
    @Override
    public void save(Reaction reaction) {
        reactionReponsitory.save(reaction);
    }

    @Override
    public void delete(Reaction reaction) {
        reactionReponsitory.delete(reaction);
    }

    @Override
    public int countReactionsByComment_IdAndType(Long commentId,String type) {
        return reactionReponsitory.countReactionsByComment_IdAndType(commentId, type);
    }

    @Override
    public int countReactionsByComment_IdAndCustomer_IdAndType(Long commentId, Long customerId, String type) {
        return reactionReponsitory.countReactionsByComment_IdAndCustomer_IdAndType(commentId, customerId, type);
    }

    @Override
    public Reaction findReactionsByCommentIdAndCustomerId(Long commentId, Long customerId) {
        return reactionReponsitory.findReactionsByCommentIdAndCustomerId(commentId, customerId);
//        return null;
    }

    @Override
    public void deleteByCommentIdAndCustomerId(Long commentId, Long customerId) {
        Reaction reaction = reactionReponsitory.findReactionsByCommentIdAndCustomerId(commentId, customerId);
        reactionReponsitory.delete(reaction);
    }

    @Override
    public JSONArray getReactionsByCommentId(Long commentId) {
        List<Reaction> reactions = reactionReponsitory.findReactionsByComment_Id(commentId);
        JSONArray reactionsJson = new JSONArray();
        for (Reaction reaction : reactions) {
            reactionsJson.add(reaction.getType());
        }
        return reactionsJson;
    }

    @Override
    public boolean checkReaction(Long commentId, Long customerId, String type) {
        Reaction reaction = reactionReponsitory.findReactionsByCommentIdAndCustomerId(commentId, customerId);
        if (reaction == null) return false;
        else return true;
    }

    @Override
    public void changeReaction(Long commentId, Long customerId, String type) {
        Reaction reaction = reactionReponsitory.findReactionsByCommentIdAndCustomerId(commentId, customerId);
        reaction.setType(type);
        reactionReponsitory.save(reaction);
    }

    @Override
    public boolean unReaction(Long commentId, Long customerId, String type) {
        Reaction reaction = reactionReponsitory.findReactionsByCommentIdAndCustomerId(commentId, customerId);
        if (reaction == null) return false;
        reactionReponsitory.delete(reaction);
        return true;
    }
}
