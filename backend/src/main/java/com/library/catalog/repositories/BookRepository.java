package com.library.catalog.repositories;

import com.library.catalog.models.Writer;
import org.springframework.data.jpa.repository.JpaRepository;
import com.library.catalog.models.Book;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Integer> {
    List<Book> findAllByWritersId(Integer id);
    List<Book> findAllByThemesId(Integer id);
}
