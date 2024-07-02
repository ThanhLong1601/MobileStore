package com.ex.mobilestore.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "manufactures", schema = "mobilestore")
public class ManufactureEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "manufacture_id", nullable = false)
    private Integer id;

    @Column(name = "manufacture_name", nullable = false, length = 50)
    private String manufactureName;

    @OneToMany(mappedBy = "manufacture")
    private Set<ProductEntity> products = new LinkedHashSet<>();

}