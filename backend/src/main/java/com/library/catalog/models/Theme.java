package com.library.catalog.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @Column(name = "id")
    private Long id;

    @Column(name = "theme_name", nullable = false, unique = true)
    @NotBlank
    private String name;

    @Column(name = "description", nullable = false)
    @NotNull
    private String description;

    @ManyToMany(mappedBy = "themes")
    @JsonIgnore
    private List<Book> books;
}
