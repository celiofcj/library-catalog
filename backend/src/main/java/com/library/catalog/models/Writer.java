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
@Table(name = Writer.TABLE_NAME)
@Entity
public class Writer {
    public static final String TABLE_NAME = "writer";
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true)
    @NotNull
    private Integer id;

    @Column(name = "name_theme", nullable = false, unique = true)
    @NotBlank
    private String name;

    @ManyToMany(mappedBy = "writers")
    private List<Book> books;
}
