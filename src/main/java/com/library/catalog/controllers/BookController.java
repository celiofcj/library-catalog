package com.library.catalog.controllers;

import com.library.catalog.dto.book.BookConverter;
import com.library.catalog.dto.book.BookInDTO;
import com.library.catalog.dto.book.BookOutDTO;
import com.library.catalog.models.Book;
import com.library.catalog.services.BookService;

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
    private BookConverter bookConverter;

    @Autowired
    public BookController(BookService bookService, BookConverter bookConverter){
        this.bookService = bookService;
        this.bookConverter = bookConverter;
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookOutDTO> findById(@PathVariable Long id){
        Book book = bookService.findById(id);
        BookOutDTO outDTO = bookConverter.entityToOutDTO(book);
        return ResponseEntity.ok(outDTO);
    }

    @GetMapping
    public ResponseEntity<List<BookOutDTO>> findAll(){
        List<Book> books = bookService.findAll();
        List<BookOutDTO> outDTOS = bookConverter.entityToOutDTO(books);

        return ResponseEntity.ok(outDTOS);
    }

    @GetMapping("/theme/{themeId}")
    public ResponseEntity<List<BookOutDTO>> findAllWithThemeId(@PathVariable Long themeId){
        List<Book> books = bookService.findByThemeId(themeId);
        List<BookOutDTO> outDTOS = bookConverter.entityToOutDTO(books);

        return ResponseEntity.ok(outDTOS);
    }

    @GetMapping("/writer/{writerId}")
    public ResponseEntity<List<BookOutDTO>> findAllWithWriterId(@PathVariable Long writerId){
        List<Book> books = bookService.findByWriterId(writerId);
        List<BookOutDTO> outDTOS = bookConverter.entityToOutDTO(books);

        return ResponseEntity.ok(outDTOS);
    }

    @PostMapping
    public ResponseEntity<BookOutDTO> create(@Valid @RequestBody BookInDTO inDTO){
        Book book = bookConverter.inDTOToEntity(inDTO);
        Book newBook = bookService.create(book);
        BookOutDTO outDTO = bookConverter.entityToOutDTO(newBook);
        URI uri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/{id}").
                buildAndExpand(newBook.getId()).toUri();

        return ResponseEntity.created(uri).body(outDTO);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<BookOutDTO> update(@PathVariable Long id, @Valid @RequestBody BookInDTO inDTO){
        Book book = bookConverter.inDTOToEntity(inDTO);
        Book bookUpdated = bookService.update(id, book);
        BookOutDTO outDTO = bookConverter.entityToOutDTO(bookUpdated);

        return ResponseEntity.ok(outDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Book> delete(@PathVariable Long id){
        bookService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
