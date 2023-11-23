package com.library.catalog.services;

import com.library.catalog.models.Writer;
import com.library.catalog.repositories.WriterRepository;
import com.library.catalog.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class WriterService {
    private WriterRepository writerRepository;

    @Autowired
    public WriterService(WriterRepository writerRepository){
        this.writerRepository = writerRepository;
    }

    @Transactional(readOnly = true)
    public Writer findById(Long id){
        Optional<Writer> writer = writerRepository.findById(id);

        return writer.orElseThrow(() -> new ObjectNotFoundException("Not found writer with id: {" +
                id + "}."));
    }

    @Transactional(readOnly = true)
    public List<Writer> findAll(){
        return writerRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<Writer> findAllByBookId(Long id){
        return writerRepository.findAllByBooksId(id);
    }

    @Transactional
    public Writer create(Writer writer){
        Writer newWriter = new Writer();

        newWriter.setName(writer.getName());
        newWriter.setBio(writer.getBio());

        return newWriter;
    }

    @Transactional
    public Writer update(Long id, Writer writer){
        Writer savedWriter = findById(id);

        savedWriter.setName(writer.getName());
        savedWriter.setBio(writer.getBio());

        return savedWriter;
    }

    @Transactional
    public void delete(Long id){
        writerRepository.deleteById(id);
    }
}
