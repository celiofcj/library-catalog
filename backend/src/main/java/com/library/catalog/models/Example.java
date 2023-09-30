package com.library.catalog.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@Table(name = Example.TABLE_NAME)
@Entity
public class Example {
    public static final String TABLE_NAME = "example";

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true)
    @NotNull
    private Integer id;

    @Column(name = "available", nullable = false)
    @NotNull
    private Boolean available;

    @ManyToOne
    private Book book;
}
