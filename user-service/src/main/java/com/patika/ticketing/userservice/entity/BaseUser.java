package com.patika.ticketing.userservice.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@MappedSuperclass
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BaseUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(min = 3, max = 20)
    @Column(name = "first_name", nullable = false, length = 20)
    private String firstName;

    @NotBlank
    @Size(min = 3, max = 20)
    @Column(name = "last_name", nullable = false, length = 20)
    private String lastName;

    @NotBlank
    @Size(max = 20)
    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @NotBlank
    @Size(max = 50)
    @Email
    @Column(name = "email", nullable = false, unique = true, length = 50)
    private String email;

    @NotBlank
    @Size(max = 120)
    @Column(name = "password", nullable = false, length = 120)
    private String password;

    @NotBlank
    @Size(max = 20)
    @Column(name = "phone_number", nullable = false, length = 20)
    private String phoneNumber;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

}
