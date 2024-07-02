package com.ex.mobilestore.repository;

import com.ex.mobilestore.entity.ManufactureEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ManufactureRepository extends JpaRepository<ManufactureEntity, Integer> {
}
