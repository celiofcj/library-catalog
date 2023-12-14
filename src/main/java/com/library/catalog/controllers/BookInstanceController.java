package com.library.catalog.controllers;

import com.library.catalog.dto.BookInstanceConverter;
import com.library.catalog.dto.BookInstanceInDTO;
import com.library.catalog.dto.BookInstanceOutDTO;
import com.library.catalog.models.Book;
import com.library.catalog.models.BookInstance;
import com.library.catalog.services.BookInstanceService;
import jakarta.validation.Valid;
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
    private BookInstanceConverter bookInstanceConverter;

    @Autowired
    public BookInstanceController(BookInstanceService bookInstanceService, BookInstanceConverter bookInstanceConverter){
        this.bookInstanceService = bookInstanceService;
        this.bookInstanceConverter = bookInstanceConverter;
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookInstanceOutDTO> findById(@PathVariable Long id){
        BookInstance bookInstance = bookInstanceService.findById(id);
        BookInstanceOutDTO outDTO = bookInstanceConverter.entityToOutDTO(bookInstance);
        return ResponseEntity.ok(outDTO);
    }

    @GetMapping
    public ResponseEntity<List<BookInstanceOutDTO>> findAll(){
        List<BookInstance> books = bookInstanceService.findAll();
        List<BookInstanceOutDTO> outDTOs = bookInstanceConverter.entityToOutDTO(books);

        return ResponseEntity.ok(outDTOs);
    }

    @GetMapping("/book/{bookId}")
    public ResponseEntity<List<BookInstanceOutDTO>> findAllByBookId(@PathVariable Long bookId){
        List<BookInstance> books = bookInstanceService.findAllByBookId(bookId);
        List<BookInstanceOutDTO> outDTOs = bookInstanceConverter.entityToOutDTO(books);

        return ResponseEntity.ok(outDTOs);
    }

    @PostMapping
    public ResponseEntity<BookInstanceOutDTO> create(@Valid @RequestBody BookInstanceInDTO inDTO){
        BookInstance bookInstance = bookInstanceConverter.inDTOToEntity(inDTO);
        BookInstance newBookInstance = bookInstanceService.create(bookInstance);
        BookInstanceOutDTO outDTO = bookInstanceConverter.entityToOutDTO(newBookInstance);
        URI uri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/{id}")
                .buildAndExpand(newBookInstance.getId()).toUri();

        return ResponseEntity.created(uri).body(outDTO);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<BookInstanceOutDTO> update(@PathVariable Long id, @Valid @RequestBody BookInstanceInDTO inDTO){
        BookInstance bookInstance = bookInstanceConverter.inDTOToEntity(inDTO);
        BookInstance updatedBookInstance = bookInstanceService.update(id, bookInstance);
        BookInstanceOutDTO outDTO = bookInstanceConverter.entityToOutDTO(updatedBookInstance);

        return ResponseEntity.ok(outDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        bookInstanceService.delete(id);

        return ResponseEntity.noContent().build();
    }

}
