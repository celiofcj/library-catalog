package com.library.catalog.controllers;

import com.library.catalog.models.Theme;
import com.library.catalog.services.ThemeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RequestMapping("/theme")
@RestController
public class ThemeController {
    private final ThemeService themeService;

    @Autowired
    public ThemeController(ThemeService themeService){
        this.themeService = themeService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Theme> findById(@PathVariable Long id){
        Theme theme = themeService.findById(id);

        return ResponseEntity.ok(theme);
    }

    @GetMapping
    public ResponseEntity<List<Theme>> findAll(){
        List<Theme> themes = themeService.findAll();

        return ResponseEntity.ok(themes);
    }

    @GetMapping("/book/{bookId}")
    public ResponseEntity<List<Theme>> findAllByBookId(@PathVariable Long bookId){
        List<Theme> themes = themeService.findAllByBooksId(bookId);

        return ResponseEntity.ok(themes);
    }

    @PostMapping
    public ResponseEntity<Theme> create(@Valid @RequestBody Theme theme){
        Theme newTheme = this.themeService.create(theme);

        URI uri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/{id}")
                .buildAndExpand(newTheme.getId()).toUri();

        return ResponseEntity.created(uri).body(theme);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Theme> update(@PathVariable Long id, @Valid @RequestBody Theme theme){
        Theme updateTheme = themeService.update(id, theme);

        return ResponseEntity.ok(updateTheme);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        themeService.delete(id);

        return ResponseEntity.noContent().build();
    }

}
