package com.library.catalog.services;

import com.library.catalog.models.Book;
import com.library.catalog.models.Theme;
import com.library.catalog.models.Writer;
import com.library.catalog.repositories.BookRepository;
import com.library.catalog.services.exceptions.InvalidArgumentException;
import com.library.catalog.services.exceptions.ObjectNotFoundException;
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

    @Transactional(readOnly = true)
    public Book findById(Long id){
        Optional<Book> book = bookRepository.findById(id);

        return book.orElseThrow(() -> new ObjectNotFoundException("Not found book with id: {" +
                                            id + "}."));
    }

    @Transactional(readOnly = true)
    public List<Book> findAll(){
        return bookRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<Book> findByThemeId(Long id){
        return bookRepository.findAllByThemesId(id);
    }

    @Transactional(readOnly = true)
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
    public Book update(Long id, Book book) {
        Book newBook = copyWithValidThemesAndWriters(book);

        Book bookSaved = findById(id);
        bookSaved.setTitle(newBook.getTitle());
        bookSaved.setPublishYear(newBook.getPublishYear());
        bookSaved.setPublisher(newBook.getPublisher());
        bookSaved.setThemes(newBook.getThemes());
        bookSaved.setWriters(newBook.getWriters());
        bookSaved.setExamples(newBook.getExamples());


        return bookRepository.save(bookSaved);
    }

    @Transactional
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
            }
            catch (InvalidDataAccessApiUsageException e){
                throw new InvalidArgumentException("Themes: List should contain objects with id attribute.");
            }
        }
        newBook.setThemes(themes);

        List<Writer> writers;
        if(book.getWriters() == null) writers = new ArrayList<>();
        else {
            try {
                writers = book.getWriters().stream()
                        .map(writer -> writerService.findById(writer.getId()))
                        .collect(Collectors.toList());
            }
            catch (InvalidDataAccessApiUsageException e){
                throw new InvalidArgumentException("Writers: List should contain objects with id attribute.");
            }
        }
        newBook.setWriters(writers);

        return newBook;
    }
}
