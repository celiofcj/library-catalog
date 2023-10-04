package com.library.catalog.services;

import com.library.catalog.models.Book;
import com.library.catalog.models.Theme;
import com.library.catalog.repositories.BookRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BookService {
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private ThemeService themeService;

    /*@Autowired
    public BookService(BookRepository bookRepository){

    }*/

    public Book findById(Integer id){
        Optional<Book> book = bookRepository.findById(id);

        return book.orElseThrow(() -> new EntityNotFoundException("Not found book with id: {" +
                                            id + "}."));
    }

    @Transactional
    public Book create(Book book){
        List<Theme> newThemes = new ArrayList<>();
        for(Theme theme : book.getThemes()){
            boolean existsByName;
            try {
                themeService.findByName(theme.getName());
                existsByName = true;
            }
            catch (EntityNotFoundException e){
                existsByName = false;
            }

            if(theme.getId() == null && !existsByName){
                 newThemes.add(themeService.create(theme));
            }
            else if(existsByName){
                newThemes.add(themeService.findByName(theme.getName()));
            }
            else{
                newThemes.add(themeService.findById(theme.getId()));
            }
        }
        book.setThemes(newThemes);

        return bookRepository.save(book);
    }
}
