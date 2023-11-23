package com.library.catalog.controllers;

import com.library.catalog.models.Writer;
import com.library.catalog.services.WriterService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RequestMapping("/writer")
@RestController
public class WriterController {
    private WriterService writerService;

    public WriterController(WriterService writerService){
        this.writerService = writerService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Writer> findById(@PathVariable Long id){
        Writer writer = writerService.findById(id);

        return ResponseEntity.ok(writer);
    }

    @GetMapping
    public ResponseEntity<List<Writer>> findAll(){
        List<Writer> writers = writerService.findAll();

        return ResponseEntity.ok(writers);
    }

    @GetMapping("/book/{bookId}")
    public ResponseEntity<List<Writer>> findAllByBookId(@PathVariable Long bookId){
        List<Writer> writers = writerService.findAllByBookId(bookId);

        return ResponseEntity.ok(writers);
    }

    @PostMapping
    public ResponseEntity<Writer> create(@RequestBody Writer writer){
        Writer newWriter = writerService.create(writer);
        URI uri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/{id}").buildAndExpand(newWriter.getId()).toUri();

        return ResponseEntity.created(uri).body(newWriter);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Writer> update(@PathVariable Long id, @RequestBody Writer writer){
        Writer updateWriter = writerService.update(id, writer);

        return ResponseEntity.ok(updateWriter);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        writerService.delete(id);

        return ResponseEntity.noContent().build();
    }
}
