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
    public ResponseEntity<Book> get(@PathVariable Long id){
        Book book = bookService.findById(id);
        return ResponseEntity.ok(book);
    }

    @GetMapping
    public ResponseEntity<List<Book>> getAll(){
        List<Book> books = bookService.findAll();

        return ResponseEntity.ok(books);
    }

    @GetMapping("/theme/{id}")
    public ResponseEntity<List<Book>> getAllWithThemeId(@PathVariable Long id){
        List<Book> books = bookService.findByThemeId(id);

        return ResponseEntity.ok(books);
    }

    @GetMapping("/writer/{id}")
    public ResponseEntity<List<Book>> getAllWithWriterId(@PathVariable Long id){
        List<Book> books = bookService.findByWriterId(id);

        return ResponseEntity.ok(books);
    }

    @PostMapping
    public ResponseEntity<Book> post(@Valid @RequestBody Book book){
        Book newBook = bookService.create(book);
        URI uri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/{id}").
                buildAndExpand(newBook.getId()).toUri();
        return ResponseEntity.created(uri).body(newBook);
    }

    @PutMapping
    public ResponseEntity<Book> put(@Valid @RequestBody Book book){
        Book newBook;
        try{
            bookService.findById(book.getId());
            newBook = bookService.update(book);
            return ResponseEntity.ok(newBook);
        }
        catch (EntityNotFoundException e){
            newBook = bookService.create(book);
            URI uri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/{id}").
                    buildAndExpand(newBook.getId()).toUri();
            return ResponseEntity.created(uri).body(newBook);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Book> delete(@PathVariable Long id){
        bookService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
