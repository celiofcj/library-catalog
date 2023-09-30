package com.library.catalog.repositories;

import com.library.catalog.models.Theme;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

public interface ThemeRepository extends JpaRepository<Theme, Long> {
    List<Theme> findAllByBooksId(Long id);
    Optional<Theme> findByName(String name);
}
