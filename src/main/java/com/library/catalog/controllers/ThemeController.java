package com.library.catalog.controllers;

import com.library.catalog.dto.theme.ThemeConverter;
import com.library.catalog.dto.theme.ThemeInDTO;
import com.library.catalog.dto.theme.ThemeOutDTO;
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
    private final ThemeConverter themeConverter;

    @Autowired
    public ThemeController(ThemeService themeService, ThemeConverter themeConverter){
        this.themeService = themeService;
        this.themeConverter = themeConverter;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ThemeOutDTO> findById(@PathVariable Long id){
        Theme theme = themeService.findById(id);
        ThemeOutDTO dto = themeConverter.entityToOutDTO(theme);

        return ResponseEntity.ok(dto);
    }

    @GetMapping
    public ResponseEntity<List<ThemeOutDTO>> findAll(){
        List<Theme> themes = themeService.findAll();
        List<ThemeOutDTO> dtos = themeConverter.entityToOutDTO(themes);
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/book/{bookId}")
    public ResponseEntity<List<ThemeOutDTO>> findAllByBookId(@PathVariable Long bookId){
        List<Theme> themes = themeService.findAllByBooksId(bookId);
        List<ThemeOutDTO> dtos = themeConverter.entityToOutDTO(themes);
        return ResponseEntity.ok(dtos);
    }

    @PostMapping
    public ResponseEntity<ThemeOutDTO> create(@Valid @RequestBody ThemeInDTO inDTO){
        Theme theme = themeConverter.inDTOToEntity(inDTO);
        Theme newTheme = this.themeService.create(theme);
        ThemeOutDTO outDTO = themeConverter.entityToOutDTO(newTheme);

        URI uri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/{id}")
                .buildAndExpand(newTheme.getId()).toUri();

        return ResponseEntity.created(uri).body(outDTO);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ThemeOutDTO> update(@PathVariable Long id, @Valid @RequestBody ThemeInDTO inDTO){
        Theme theme = themeConverter.inDTOToEntity(inDTO);
        Theme updatedTheme = themeService.update(id, theme);
        ThemeOutDTO outDTO = themeConverter.entityToOutDTO(updatedTheme);

        return ResponseEntity.ok(outDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        themeService.delete(id);

        return ResponseEntity.noContent().build();
    }

}
