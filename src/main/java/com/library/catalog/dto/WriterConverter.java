package com.library.catalog.dto;

import com.library.catalog.models.Book;
import com.library.catalog.models.Writer;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class WriterConverter {
    public Writer inDtoToEntity(WriterInDTO dto){
        Writer writer = new Writer();
        writer.setName(dto.getName());
        writer.setBio(dto.getBio());

        return writer;
    }

    public WriterOutDTO entityToOutDTO(Writer writer){
        WriterOutDTO dto = new WriterOutDTO();

        dto.setId(writer.getId());
        dto.setName(writer.getName());
        dto.setBio(writer.getBio());

        if(writer.getBooks() != null){
            List<Long> books = writer.getBooks().stream().map(Book::getId).collect(Collectors.toList());
            dto.setBooks(books);
        }

        return dto;
    }

    public List<Writer> inDtoToEntity(List<WriterInDTO> dtos){
        return dtos.stream().map(this::inDtoToEntity).collect(Collectors.toList());
    }

    public List<WriterOutDTO> entityToOutDTO(List<Writer> writers){
        return writers.stream().map(this::entityToOutDTO).collect(Collectors.toList());
    }
}
