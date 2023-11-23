package com.library.catalog.controllers;

import com.library.catalog.models.BookInstance;
import com.library.catalog.services.BookInstanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/instance")
public class BookInstanceController {
    private BookInstanceService bookInstanceService;

    @Autowired
    public BookInstanceController(BookInstanceService bookInstanceService){
        this.bookInstanceService = bookInstanceService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookInstance> findById(@PathVariable Long id){
        BookInstance bookInstance = bookInstanceService.findById(id);

        return ResponseEntity.ok(bookInstance);
    }

    @GetMapping
    public ResponseEntity<List<BookInstance>> findAll(){
        List<BookInstance> books = bookInstanceService.findAll();

        return ResponseEntity.ok(books);
    }

    @GetMapping("/book/{bookId}")
    public ResponseEntity<List<BookInstance>> findAllByBookId(@PathVariable Long bookId){
        List<BookInstance> books = bookInstanceService.findAllByBookId(bookId);

        return ResponseEntity.ok(books);
    }

    @PostMapping
    public ResponseEntity<BookInstance> create(@RequestBody BookInstance bookInstance){
        BookInstance newBookInstance = bookInstanceService.create(bookInstance);
        URI uri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/{id}")
                .buildAndExpand(newBookInstance.getId()).toUri();

        return ResponseEntity.created(uri).body(newBookInstance);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<BookInstance> update(@PathVariable Long id, @RequestBody BookInstance bookInstance){
        BookInstance updatedBookInstance = bookInstanceService.update(id, bookInstance);

        return ResponseEntity.ok(bookInstance);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        bookInstanceService.delete(id);

        return ResponseEntity.noContent().build();
    }

}
