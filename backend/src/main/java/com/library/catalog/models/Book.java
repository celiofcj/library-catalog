package com.library.catalog.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.Hibernate;

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
    private Long id;

    @Column(name = "title", unique = true, nullable = false)
    @NotBlank
    private String title;

    @Column(name = "publish_year")
    private Integer publishYear;

    @Column(name = "publisher")
    private String publisher;

    @ManyToMany(cascade = {CascadeType.PERSIST})
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

    public Book(Book book){
        this.id = book.id;
        this.title = book.title;
        this.publishYear = book.publishYear;
        this.publisher = book.publisher;
        this.themes = book.themes;
        this.writers = book.writers;
        this.examples = book.examples;
    }


}
