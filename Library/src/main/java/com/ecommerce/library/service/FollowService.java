package com.ecommerce.library.service;

import com.ecommerce.library.model.Customer;

import java.util.List;

public interface FollowService {
    public boolean isFollowing(Long followerId, Long followingId);
    public void follow(Customer follower, Customer following);
    public  void unFollow(Customer follower, Customer following);

    public List<Customer> findAllByFollowingId(Long followingId);
}
