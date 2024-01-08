package com.library.catalog.dto.bookinstance;

import com.library.catalog.dto.book.BookConverter;
import com.library.catalog.models.Book;
import com.library.catalog.models.BookInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class BookInstanceConverter {
    private BookConverter bookConverter;

    @Autowired
    public BookInstanceConverter(BookConverter bookConverter) {
        this.bookConverter = bookConverter;
    }

    public BookInstance inDTOToEntity(BookInstanceInDTO dto){
        BookInstance bookInstance = new BookInstance();
        bookInstance.setAvailable(dto.getAvailable());

        Book book = new Book();
        book.setId(dto.getBook());
        bookInstance.setBook(book);

        return bookInstance;
    }

    public BookInstanceOutDTO entityToOutDTO(BookInstance bookInstance){
        BookInstanceOutDTO dto = new BookInstanceOutDTO();
        dto.setId(bookInstance.getId());
        dto.setAvailable(bookInstance.getAvailable());
        if(bookInstance.getBook() != null) {
            dto.setBook(bookInstance.getBook().getId());
        }

        return dto;
    }

    public List<BookInstance> inDTOtoEntity(List<BookInstanceInDTO> dtos){
        return dtos.stream().map(this::inDTOToEntity).collect(Collectors.toList());
    }

    public List<BookInstanceOutDTO> entityToOutDTO(List<BookInstance> themes){
        return themes.stream().map(this::entityToOutDTO).collect(Collectors.toList());
    }
}
