package com.library.catalog.services;

import com.library.catalog.models.Theme;
import com.library.catalog.repositories.ThemeRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ThemeService {
    @Autowired
    ThemeRepository themeRepository;

    public Theme findById(Long id){
        Optional<Theme> theme = themeRepository.findById(id);

        return theme.orElseThrow(() -> new EntityNotFoundException("Not found theme with id: {" +
                id + "}."));
    }

    public Theme findByName(String name){
        Optional<Theme> theme = themeRepository.findByName(name);

        return theme.orElseThrow(() -> new EntityNotFoundException("Not found theme with name: {" +
                name + "}."));
    }

    @Transactional
    public Theme create(Theme theme){
        return themeRepository.save(theme);
    }
}
