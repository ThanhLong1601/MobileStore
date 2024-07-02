package com.ex.mobilestore.repository;

import com.ex.mobilestore.entity.CartProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartProductRepository extends JpaRepository<CartProductEntity, Integer> {
}
