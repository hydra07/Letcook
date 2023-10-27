package com.ecommerce.library.repository;

import com.ecommerce.library.model.Customer;
import com.ecommerce.library.model.Follow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface FollowRepository extends JpaRepository<Follow, Long> {
    @Query(value = "SELECT f FROM Follow f WHERE f.follower.id = ?1 AND f.following.id = ?2")
    Follow findByFollowerIdAndFollowingId(Long followerId, Long followingId);

    @Query(value = "SELECT f.follower FROM Follow f WHERE f.following.id = ?1")
    List<Customer> findAllByFollowingId(Long followingId);

}
