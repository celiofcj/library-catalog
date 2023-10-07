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


@RequestMapping("/book")
@RestController
public class BookController {
    @Autowired
    private BookService bookService;

    /*@Autowired
    public BookController(BookService bookService){

    }*/

    @GetMapping("/{id}")
    public ResponseEntity<Book> get(@PathVariable Integer id){
        Book book = bookService.findById(id);
        return ResponseEntity.ok(book);
    }

    @PostMapping
    public ResponseEntity<Book> post(@Valid @RequestBody Book book){
        Book newBook = bookService.create(book);
        URI uri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/{id}").
                buildAndExpand(newBook.getId()).toUri();
        return ResponseEntity.created(uri).build();
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
            return ResponseEntity.created(uri).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Book> delete(@PathVariable Integer id){
        bookService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
