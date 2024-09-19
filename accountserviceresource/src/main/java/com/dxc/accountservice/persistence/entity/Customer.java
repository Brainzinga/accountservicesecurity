package com.dxc.accountservice.persistence.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
//@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "customers")
@Builder
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String email;

//    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
//    private List<Account> accounts;

}
