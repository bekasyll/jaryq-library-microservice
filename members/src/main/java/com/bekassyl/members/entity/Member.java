package com.bekassyl.members.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "members")
public class Member extends BaseEntity {
    @Id
    @Column(name = "card_number", nullable = false, unique = true)
    private String cardNumber;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "iin", nullable = false, unique = true)
    private String iin;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "mobile_number", nullable = false, unique = true)
    private String mobileNumber;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "communication_status")
    private boolean communicationStatus;
}


