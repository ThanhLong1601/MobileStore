package com.ex.mobilestore.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "users", schema = "mobilestore")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", nullable = false)
    private Integer id;

    @Column(name = "user_name", nullable = false, length = 50)
    @NotBlank(message = "Username cannot be blank")
    @Size(min = 5, max = 50, message = "Username must be longer than 5 or less than 50 characters")
    private String username;

    @Column(name = "pass_word", nullable = false)
    @NotBlank(message = "Password cannot be blank")
    private String password;

    @OneToMany(mappedBy = "user")
    private Set<CartEntity> carts = new LinkedHashSet<>();

    @OneToMany(mappedBy = "user")
    private Set<OrderEntity> orders = new LinkedHashSet<>();

    @OneToOne(mappedBy = "user")
    private UserDetailEntity userDetails;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "role_id"))
    private Set<RoleEntity> roles;

}