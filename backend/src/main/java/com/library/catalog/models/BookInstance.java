package com.library.catalog.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = BookInstance.TABLE_NAME)
@Entity
public class BookInstance {
    public static final String TABLE_NAME = "instance";

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true)
    private Long id;

    @Column(name = "available", nullable = false)
    @NotNull
    private Boolean available;

    @ManyToOne
    @JsonIgnore
    private Book book;
}
