package com.library.catalog.dto;

import com.library.catalog.models.Book;
import com.library.catalog.models.Theme;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ThemeConverter {
    public Theme inDTOToEntity(ThemeInDTO dto){
        Theme theme = new Theme();
        theme.setName(dto.getName());
        theme.setDescription(dto.getDescription());

        return theme;
    }

    public ThemeOutDTO entityToOutDTO(Theme theme){
        ThemeOutDTO dto = new ThemeOutDTO();

        dto.setId(theme.getId());
        dto.setName(theme.getName());
        dto.setDescription(theme.getDescription());
        if(theme.getBooks() != null){
            List<Long> books = theme.getBooks().stream().map(Book::getId).collect(Collectors.toList());
            dto.setBooks(books);
        }

        return dto;
    }

    public List<Theme> inDTOtoEntity(List<ThemeInDTO> dtos){
        return dtos.stream().map(this::inDTOToEntity).collect(Collectors.toList());
    }

    public List<ThemeOutDTO> entityToOutDTO(List<Theme> themes){
        return themes.stream().map(this::entityToOutDTO).collect(Collectors.toList());
    }
}
