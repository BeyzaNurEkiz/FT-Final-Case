package com.patika.ticketing.userservice.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Entity
@Table(name = "corporate_users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CorporateUser extends BaseUser{

    @NotBlank
    @Size(min = 10, max = 10)
    @Column(name = "tax_number", nullable = false, unique = true, length = 10)
    private String taxNumber;

}
