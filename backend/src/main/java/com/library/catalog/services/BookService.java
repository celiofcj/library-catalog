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
    private BookRepository bookRepository;
    private ThemeService themeService;

    @Autowired
    public BookService(BookRepository bookRepository, ThemeService themeService){
        this.bookRepository = bookRepository;
        this.themeService = themeService;
    }

    public Book findById(Long id){
        Optional<Book> book = bookRepository.findById(id);

        return book.orElseThrow(() -> new EntityNotFoundException("Not found book with id: {" +
                                            id + "}."));
    }

    @Transactional
    public Book create(Book book){
        book.setId(null);
        List<Theme> themes = getThemes(book);
        Book newBook = new Book(book);
        newBook.setThemes(themes);

        return bookRepository.save(newBook);
    }

    @Transactional
    public Book update(Book book) {
            List<Theme> themes = getThemes(book);
            Book newBook = new Book(book);
            newBook.setThemes(themes);

            return bookRepository.save(newBook);
    }

    public void delete(Long id){
        bookRepository.delete(findById(id));
    }

    private List<Theme> getThemes(Book book) {
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
        return newThemes;
    }
}
