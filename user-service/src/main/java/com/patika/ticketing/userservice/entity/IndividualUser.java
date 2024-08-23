package com.patika.ticketing.userservice.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "individual_users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class IndividualUser extends BaseUser {

    @NotBlank
    @Size(min = 11, max = 11)
    @Column(name = "tc_number", nullable = false, unique = true, length = 11)
    private String tcNumber;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "individual_user_roles",
            joinColumns = @JoinColumn(name = "individual_user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    public IndividualUser(String username, String email, String password, String firstName, String lastName, String phoneNumber, String tcNumber) {
        super(firstName, lastName, username, email, password, phoneNumber);
        this.tcNumber = tcNumber;
    }
}
