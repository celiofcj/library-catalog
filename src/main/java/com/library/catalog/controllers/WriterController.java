package com.library.catalog.controllers;

import com.library.catalog.dto.WriterConverter;
import com.library.catalog.dto.WriterInDTO;
import com.library.catalog.dto.WriterOutDTO;
import com.library.catalog.models.Writer;
import com.library.catalog.services.WriterService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RequestMapping("/writer")
@RestController
public class WriterController {
    private WriterService writerService;
    private WriterConverter writerConverter;

    @Autowired
    public WriterController(WriterService writerService, WriterConverter writerConverter){
        this.writerService = writerService;
        this.writerConverter = writerConverter;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Writer> findById(@PathVariable Long id){
        Writer writer = writerService.findById(id);

        return ResponseEntity.ok(writer);
    }

    @GetMapping
    public ResponseEntity<List<WriterOutDTO>> findAll(){
        List<Writer> writers = writerService.findAll();
        List<WriterOutDTO> dtos = writerConverter.entityToOutDTO(writers);
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/book/{bookId}")
    public ResponseEntity<List<WriterOutDTO>> findAllByBookId(@PathVariable Long bookId){
        List<Writer> writers = writerService.findAllByBookId(bookId);

        List<WriterOutDTO> dtos = writerConverter.entityToOutDTO(writers);
        return ResponseEntity.ok(dtos);
    }

    @PostMapping
    public ResponseEntity<WriterOutDTO> create(@Valid @RequestBody WriterInDTO inDTO){
        Writer writer = writerConverter.inDtoToEntity(inDTO);
        Writer newWriter = writerService.create(writer);
        WriterOutDTO outDTO = writerConverter.entityToOutDTO(newWriter);
        URI uri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/{id}").buildAndExpand(newWriter.getId()).toUri();
        return ResponseEntity.created(uri).body(outDTO);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<WriterOutDTO> update(@PathVariable Long id, @Valid @RequestBody WriterInDTO inDTO){
        Writer writer = writerConverter.inDtoToEntity(inDTO);
        Writer updatedWriter = writerService.update(id, writer);
        WriterOutDTO outDTO = writerConverter.entityToOutDTO(updatedWriter);
        return ResponseEntity.ok(outDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        writerService.delete(id);

        return ResponseEntity.noContent().build();
    }
}
