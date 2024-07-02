package com.ex.mobilestore.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "products", schema = "mobilestore")
public class ProductEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id", nullable = false)
    private Integer id;

    @Column(name = "product_name", nullable = false)
    private String productName;

    @Column(name = "product_image")
    private String productImage;

    @Lob
    @Column(name = "product_description")
    private String productDescription;

    @Column(name = "price", nullable = false, precision = 10, scale = 2)
    @Digits(integer = 10, fraction = 2, message = "Price must be entered numerically")
    private BigDecimal price;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "manufacture_id", nullable = false)
    private ManufactureEntity manufacture;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "category_id", nullable = false)
    private CategoryEntity category;

    @Column(name = "stock_quantity", nullable = false)
    @Digits(integer = Integer.MAX_VALUE, fraction = 0,message = "Stock quantity must be a valid integer")
    @PositiveOrZero(message = "Stock quantity cannot be negative")
    private Integer stockQuantity;

    @OneToMany(mappedBy = "product")
    private Set<CartProductEntity> cartProducts = new LinkedHashSet<>();

    @OneToMany(mappedBy = "product")
    private Set<OrderItemEntity> orderItems = new LinkedHashSet<>();

    @OneToOne(mappedBy = "product")
    private ProductDetailEntity productDetails;

}