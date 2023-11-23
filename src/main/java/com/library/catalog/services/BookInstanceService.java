package com.library.catalog.services;

import com.library.catalog.models.BookInstance;
import com.library.catalog.repositories.BookInstanceRepository;
import com.library.catalog.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.List;

@Service
public class BookInstanceService {
    private BookInstanceRepository bookInstanceRepository;

    @Autowired
    public BookInstanceService(BookInstanceRepository bookInstanceRepository){
        this.bookInstanceRepository = bookInstanceRepository;
    }

    @Transactional(readOnly = true)
    public BookInstance findById(Long id){
        Optional<BookInstance> bookInstance = bookInstanceRepository.findById(id);

        return bookInstance.orElseThrow(() -> new ObjectNotFoundException("Not found book instance with id: {" +
                                                    id + "}."));
    }

    @Transactional(readOnly = true)
    public List<BookInstance> findAll(){
        return bookInstanceRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<BookInstance> findAllByBookId(Long id){
        return bookInstanceRepository.findAllByBookId(id);
    }

    @Transactional
    public BookInstance create(BookInstance bookInstance){
        BookInstance newBookInstance = new BookInstance();

        newBookInstance.setBook(bookInstance.getBook());
        newBookInstance.setAvailable(bookInstance.getAvailable());

        return bookInstanceRepository.save(newBookInstance);
    }

    @Transactional
    public BookInstance update(Long id, BookInstance bookInstance){
        BookInstance savedBookInstance = findById(id);

        savedBookInstance.setBook(bookInstance.getBook());
        savedBookInstance.setAvailable(bookInstance.getAvailable());

        return savedBookInstance;
    }

    @Transactional
    public void delete(Long id){
        bookInstanceRepository.deleteById(id);
    }
}
