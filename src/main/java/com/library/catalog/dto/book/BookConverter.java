package com.library.catalog.dto.book;

import com.library.catalog.dto.writer.WriterConverter;
import com.library.catalog.dto.writer.WriterOutDTO;
import com.library.catalog.dto.theme.ThemeConverter;
import com.library.catalog.dto.theme.ThemeOutDTO;
import com.library.catalog.models.Book;
import com.library.catalog.models.Theme;
import com.library.catalog.models.Writer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class BookConverter {

    private ThemeConverter themeConverter;
    private WriterConverter writerConverter;

    @Autowired
    public BookConverter(ThemeConverter themeConverter, WriterConverter writerConverter) {
        this.themeConverter = themeConverter;
        this.writerConverter = writerConverter;
    }

    public Book inDTOToEntity(BookInDTO dto){
        Book book = new Book();
        book.setTitle(dto.getTitle());
        book.setPublishYear(dto.getPublishYear());
        book.setPublisher(dto.getPublisher());

        if(dto.getThemes() != null) {
            List<Theme> themes = dto.getThemes().stream()
                    .map(themeId -> {
                        Theme t = new Theme();
                        t.setId(themeId);
                        return t;
                    }).collect(Collectors.toList());
            book.setThemes(themes);
        }

        if(dto.getWriters() != null){
            List<Writer> writers = dto.getWriters().stream()
                    .map(writerId -> {
                        Writer w = new Writer();
                        w.setId(writerId);
                        return w;
                    })
                    .collect(Collectors.toList());
            book.setWriters(writers);
        }

        return book;
    }

    public BookOutDTO entityToOutDTO(Book book){
        BookOutDTO dto = new BookOutDTO();

        dto.setId(book.getId());
        dto.setTitle(book.getTitle());
        dto.setPublishYear(book.getPublishYear());
        dto.setPublisher(book.getPublisher());

        if(book.getThemes() != null){
            List<ThemeOutDTO> themes = book.getThemes().stream()
                    .map(theme -> themeConverter.entityToOutDTO(theme))
                    .collect(Collectors.toList());
            dto.setThemes(themes);
        }

        if(book.getWriters() != null){
            List<WriterOutDTO> writers = book.getWriters().stream()
                    .map(writer -> writerConverter.entityToOutDTO(writer))
                    .collect(Collectors.toList());
            dto.setWriters(writers);
        }

        return dto;
    }

    public List<Book> inDTOToEntity(List<BookInDTO> dtos){
        return dtos.stream().map(this::inDTOToEntity).collect(Collectors.toList());
    }

    public List<BookOutDTO> entityToOutDTO(List<Book> books){
        return books.stream().map(this::entityToOutDTO).collect(Collectors.toList());
    }
}
