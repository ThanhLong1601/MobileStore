package com.ex.mobilestore.repository;

import com.ex.mobilestore.entity.ProductEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity,Integer> {
    Optional<ProductEntity> findById(Integer id);

    @Query("select p from ProductEntity p where p.price >= :price")
    Page<ProductEntity> findAllByPrice(BigDecimal price, Pageable pageable);

    @Query("select p from ProductEntity p where p.category.categoryName like %:categoryName%")
    Page<ProductEntity> findAllByCategoryName(String categoryName, Pageable pageable);
}
