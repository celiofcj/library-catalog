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

    @Column(name = "rentable", nullable = false)
    @NotNull
    private Boolean rentable;

    @ManyToMany
    @JoinTable(name = "theme_association",
            joinColumns = @JoinColumn(referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(referencedColumnName = "id_theme"))
    private List<Theme> themes;


    @ManyToMany
    @JoinTable(name = "book_writer",
            joinColumns = @JoinColumn(referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(referencedColumnName = "id"))
    private List<Writer> writers;

    /*
    @OneToMany
    private List<Example> examples = new ArrayList<>();
    */

}
