package com.ex.mobilestore.entity;

import com.ex.mobilestore.constant.CartStatus;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.math.BigDecimal;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "carts", schema = "mobilestore")
public class CartEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private UserEntity user;

    @OneToOne(mappedBy = "cart", fetch = FetchType.LAZY)
    private OrderEntity order;

    @Column(name = "total_product", precision = 10, scale = 2)
    private BigDecimal totalProduct;

    // TODO: Them ngay tao va status:
    @Enumerated(EnumType.STRING)
    @Column(name = "cart_status")
    private CartStatus status;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_date", nullable = false, updatable = false)
    private Date createdDate;

    @PrePersist
    protected void onCreate() {
        createdDate = new Date();
    }

    @OneToMany(mappedBy = "cart")
    private Set<CartProductEntity> cartProducts = new LinkedHashSet<>();

}