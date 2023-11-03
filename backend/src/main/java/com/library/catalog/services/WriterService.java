package com.library.catalog.services;

import com.library.catalog.models.Writer;
import com.library.catalog.repositories.WriterRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class WriterService {
    private WriterRepository writerRepository;

    @Autowired
    public WriterService(WriterRepository writerRepository){
        this.writerRepository = writerRepository;
    }

    public Writer findById(Long id){
        Optional<Writer> writer = writerRepository.findById(id);

        return writer.orElseThrow(() -> new EntityNotFoundException("Not found writer with id: {" +
                id + "}."));
    }
}
