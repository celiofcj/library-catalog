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
    @Column(name = "id")
    private Long id;

    @Column(name = "writer_name", nullable = false)
    @NotBlank
    private String name;

    @Column(name = "bio", nullable = false)
    @NotNull
    private String bio;

    @ManyToMany(mappedBy = "writers")
    @JsonIgnore
    private List<Book> books;
}
