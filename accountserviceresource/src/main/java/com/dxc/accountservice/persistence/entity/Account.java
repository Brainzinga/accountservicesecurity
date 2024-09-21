package com.dxc.accountservice.persistence.entity;


import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Setter
@Builder
@ToString
//@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "accounts")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String type;

    @Column(name = "opening_date")
    private LocalDate  openingDate;

    private int balance;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private Customer customer;
}
