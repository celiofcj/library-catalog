package com.library.catalog.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = Theme.TABLE_NAME)
@Entity
public class Theme {
    public static final String TABLE_NAME = "theme";
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true)
    @NotNull
    private Integer id;

    @Column(name = "theme_name", unique = true, nullable = false)
    @NotBlank
    private String name;

    @ManyToMany(mappedBy = "themes")
    private List<Book> books;
}
