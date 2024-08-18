package com.patika.ticketing.userservice.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

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

}
