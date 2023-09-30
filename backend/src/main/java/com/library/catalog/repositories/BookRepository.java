package com.library.catalog.repositories;

import com.library.catalog.models.Writer;
import org.springframework.data.jpa.repository.JpaRepository;
import com.library.catalog.models.Book;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findAllByWritersId(Long id);
    List<Book> findAllByThemesId(Long id);
}
