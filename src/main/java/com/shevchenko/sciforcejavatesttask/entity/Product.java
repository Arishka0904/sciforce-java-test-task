package com.shevchenko.sciforcejavatesttask.entity;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@EqualsAndHashCode(of = "productUuid")
@ToString
@NoArgsConstructor
@Entity
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "productUuid", nullable = false, unique = true)
    private String productUuid;

    @Column(name = "productName", nullable = false)
    private String productName;

    @Column(name = "amount")
    private int amount;
}
