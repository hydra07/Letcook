package com.ecommerce.library.service.impl;

import com.ecommerce.library.model.Customer;
import com.ecommerce.library.model.Follow;
import com.ecommerce.library.repository.FollowRepository;
import com.ecommerce.library.service.FollowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FollowServiceImpl implements FollowService{
    @Autowired
    private FollowRepository followRepository;

    @Autowired
    private CustomerServiceImpl customerService;
    @Override
    public boolean isFollowing(Long followerId, Long followingId) {
        Follow follow = followRepository.findByFollowerIdAndFollowingId(followerId, followingId);
        if(follow == null) {
            return false;
        }
        return true;
    }

    @Override
    public void follow(Customer follower, Customer following) {
        Follow follow = new Follow();
        follow.setFollower(follower);
        follow.setFollowing(following);
        followRepository.save(follow);
    }

    @Override
    public void unFollow(Customer follower, Customer following) {
        Follow follow = followRepository.findByFollowerIdAndFollowingId(follower.getId(), following.getId());
        followRepository.delete(follow);
    }

    @Override
    public List<Customer> findAllByFollowingId(Long followingId) {
        return followRepository.findAllByFollowingId(followingId);
    }
}
