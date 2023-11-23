package com.library.catalog.controllers;

import com.library.catalog.models.Book;
import com.library.catalog.services.BookService;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RequestMapping("/book")
@RestController
public class BookController {
    private BookService bookService;

    @Autowired
    public BookController(BookService bookService){
        this.bookService = bookService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> findById(@PathVariable Long id){
        Book book = bookService.findById(id);
        return ResponseEntity.ok(book);
    }

    @GetMapping
    public ResponseEntity<List<Book>> findAll(){
        List<Book> books = bookService.findAll();

        return ResponseEntity.ok(books);
    }

    @GetMapping("/theme/{themeId}")
    public ResponseEntity<List<Book>> findAllWithThemeId(@PathVariable Long themeId){
        List<Book> books = bookService.findByThemeId(themeId);

        return ResponseEntity.ok(books);
    }

    @GetMapping("/writer/{writerId}")
    public ResponseEntity<List<Book>> findAllWithWriterId(@PathVariable Long writerId){
        List<Book> books = bookService.findByWriterId(writerId);

        return ResponseEntity.ok(books);
    }

    @PostMapping
    public ResponseEntity<Book> create(@Valid @RequestBody Book book){
        Book newBook = bookService.create(book);
        URI uri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/{id}").
                buildAndExpand(newBook.getId()).toUri();
        return ResponseEntity.created(uri).body(newBook);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Book> update(@PathVariable Long id, @RequestBody Book book){
        Book bookUpdated = bookService.update(id, book);

        return ResponseEntity.ok(bookUpdated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Book> delete(@PathVariable Long id){
        bookService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
