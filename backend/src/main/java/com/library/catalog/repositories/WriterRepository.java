package com.library.catalog.repositories;

import com.library.catalog.models.Writer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface WriterRepository extends JpaRepository<Writer, Integer> {
    List<Writer> findAllByBooksId(Integer id);
}
