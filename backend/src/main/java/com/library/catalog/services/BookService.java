package com.library.catalog.services;

import com.library.catalog.exception.InvalidArgumentException;
import com.library.catalog.models.Book;
import com.library.catalog.models.Theme;
import com.library.catalog.models.Writer;
import com.library.catalog.repositories.BookRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookService {
    private final BookRepository bookRepository;
    private final ThemeService themeService;
    private final WriterService writerService;

    @Autowired
    public BookService(BookRepository bookRepository, ThemeService themeService, WriterService writerService){
        this.bookRepository = bookRepository;
        this.themeService = themeService;
        this.writerService = writerService;
    }

    public Book findById(Long id){
        Optional<Book> book = bookRepository.findById(id);

        return book.orElseThrow(() -> new EntityNotFoundException("Not found book with id: {" +
                                            id + "}."));
    }

    public List<Book> findAll(){
        return bookRepository.findAll();
    }

    public List<Book> findByThemeId(Long id){
        return bookRepository.findAllByThemesId(id);
    }

    public List<Book> findByWriterId(Long id){
        return bookRepository.findAllByWritersId(id);
    }

    @Transactional
    public Book create(Book book){
        Book newBook = copyWithValidThemesAndWriters(book);
        newBook.setId(null);

        return bookRepository.save(newBook);
    }

    @Transactional
    public Book update(Book book) {
        Book newBook = copyWithValidThemesAndWriters(book);

        Book bookSaved = findById(newBook.getId());
        bookSaved.setTitle(newBook.getTitle());
        bookSaved.setPublishYear(newBook.getPublishYear());
        bookSaved.setPublisher(newBook.getPublisher());
        bookSaved.setThemes(newBook.getThemes());
        bookSaved.setWriters(newBook.getWriters());
        bookSaved.setExamples(newBook.getExamples());


        return bookRepository.save(bookSaved);
    }

    public void delete(Long id){
        bookRepository.delete(findById(id));
    }

    private Book copyWithValidThemesAndWriters(Book book){
        Book newBook = new Book(book);
        List<Theme> themes;
        if(book.getThemes() == null) themes = new ArrayList<>();
        else {
            try {
                themes = book.getThemes().stream()
                        .map(theme -> themeService.findById(theme.getId()))
                        .collect(Collectors.toList());
                newBook.setThemes(themes);
            }
            catch (InvalidDataAccessApiUsageException e){
                throw new InvalidArgumentException("Themes: Array should contain objects with id attribute.");
            }
        }

        List<Writer> writers;
        if(book.getWriters() == null) writers = new ArrayList<>();
        else {
            try {
                writers = book.getWriters().stream()
                        .map(writer -> writerService.findById(writer.getId()))
                        .collect(Collectors.toList());
                newBook.setWriters(writers);
            }
            catch (InvalidDataAccessApiUsageException e){
                throw new InvalidArgumentException("Writers: Array should contain objects with id attribute.");
            }
        }

        return newBook;
    }
}
