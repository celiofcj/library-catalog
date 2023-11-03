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
@Table(name = Writer.TABLE_NAME)
@Entity
public class Writer {
    public static final String TABLE_NAME = "writer";
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true)
    private Long id;

    @Column(name = "writer_name", nullable = false, unique = true)
    @NotBlank
    private String name;

    @ManyToMany(mappedBy = "writers")
    @JsonIgnore
    private List<Book> books;
}
