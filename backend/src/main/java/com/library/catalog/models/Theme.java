package com.library.catalog.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
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
    private Long id;

    @Column(name = "theme_name", unique = true, nullable = false)
    @NotBlank
    private String name;

    @ManyToMany(mappedBy = "themes")
    @JsonIgnore
    private List<Book> books;
}
