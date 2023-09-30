package com.library.catalog.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

@Table(name = Theme.TABLE_NAME)
@Entity
public class Theme {
    public static final String TABLE_NAME = "theme";
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_theme", unique = true)
    @NotNull
    private Integer id;

    @Column(name = "theme_name", unique = true, nullable = false)
    @NotBlank
    private String name;

    @ManyToMany(mappedBy = "themes")
    private List<Book> books;
}
