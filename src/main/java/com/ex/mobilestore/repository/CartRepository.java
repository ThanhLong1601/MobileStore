package com.ex.mobilestore.repository;

import com.ex.mobilestore.constant.CartStatus;
import com.ex.mobilestore.entity.CartEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<CartEntity, Integer> {
    Optional<CartEntity> findById(Integer id);
    Optional<CartEntity> findByUserIdAndStatus(Integer userId, CartStatus status);
}
