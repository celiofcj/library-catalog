package com.library.catalog.repositories;

import com.library.catalog.models.BookInstance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface BookInstanceRepository extends JpaRepository<BookInstance, Integer> {
    List<BookInstance> findAllByBookId(Integer id);
}
