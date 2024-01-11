package com.library.catalog.services;

import com.library.catalog.models.Book;
import com.library.catalog.repositories.BookRepository;
import com.library.catalog.services.exceptions.InvalidArgumentException;
import com.library.catalog.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Service
public class BookService {
    private final BookRepository bookRepository;
    @Autowired
    public BookService(BookRepository bookRepository){
        this.bookRepository = bookRepository;
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
        book.setId(null);

        return bookRepository.save(book);
    }

    @Transactional
    public Book update(Long id, Book book) {
        Book bookSaved = findById(id);
        bookSaved.setTitle(book.getTitle());
        bookSaved.setPublishYear(book.getPublishYear());
        bookSaved.setPublisher(book.getPublisher());
        bookSaved.setThemes(book.getThemes());
        bookSaved.setWriters(book.getWriters());
        bookSaved.setExamples(book.getExamples());


        return bookRepository.save(book);
    }

    @Transactional
    public void delete(Long id){
        bookRepository.deleteById(id);
    }
}
