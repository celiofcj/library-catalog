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
@Table(name = Book.TABLE_NAME)
@Entity
public class Book {
    public static final String TABLE_NAME = "book";

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true)
    @NotNull
    private Integer id;

    @Column(name = "title", unique = true, nullable = false)
    @NotBlank
    private String title;

    @Column(name = "publish_year")
    private Integer publishYear;

    @Column(name = "publisher")
    private String publisher;

    @ManyToMany
    @JoinTable(name = "theme_book",
            joinColumns = @JoinColumn(referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(referencedColumnName = "id"))
    private List<Theme> themes;


    @ManyToMany
    @JoinTable(name = "writer_book",
            joinColumns = @JoinColumn(referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(referencedColumnName = "id"))
    private List<Writer> writers;

    @OneToMany(mappedBy = "book")
    private List<BookInstance> examples;

}
