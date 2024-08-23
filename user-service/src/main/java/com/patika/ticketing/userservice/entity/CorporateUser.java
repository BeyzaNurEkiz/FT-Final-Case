package com.patika.ticketing.userservice.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "corporate_users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CorporateUser extends BaseUser {

    @NotBlank
    @Size(min = 10, max = 10)
    @Column(name = "tax_number", nullable = false, unique = true, length = 10)
    private String taxNumber;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "corporate_user_roles",
            joinColumns = @JoinColumn(name = "corporate_user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    public CorporateUser(String username, String email, String password, String firstName, String lastName, String phoneNumber, String taxNumber) {
        super(firstName, lastName, username, email, password, phoneNumber);
        this.taxNumber = taxNumber;
    }
}
