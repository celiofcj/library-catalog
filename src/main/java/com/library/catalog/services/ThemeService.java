package com.library.catalog.services;

import com.library.catalog.models.Theme;
import com.library.catalog.repositories.ThemeRepository;
import com.library.catalog.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ThemeService {
    ThemeRepository themeRepository;

    @Autowired
    public ThemeService(ThemeRepository themeRepository){
        this.themeRepository = themeRepository;
    }

    @Transactional(readOnly = true)
    public Theme findById(Long id){
        Optional<Theme> theme = themeRepository.findById(id);

        return theme.orElseThrow(() -> new ObjectNotFoundException("Not found theme with id: {" +
                id + "}."));
    }

    @Transactional(readOnly = true)
    public List<Theme> findAll(){
        return themeRepository.findAll();
    }


    @Transactional(readOnly = true)
    public List<Theme> findAllByBooksId(Long id){
        return themeRepository.findAllByBooksId(id);
    }

    @Transactional
    public Theme create(Theme theme){
        Theme newTheme = new Theme();
        newTheme.setName(theme.getName());
        newTheme.setDescription(theme.getDescription());

        return themeRepository.save(newTheme);
    }

    @Transactional
    public Theme update(Long id, Theme theme){
        Theme themeSaved = findById(id);

        themeSaved.setName(theme.getName());
        themeSaved.setDescription(theme.getDescription());

        return themeSaved;
    }

    @Transactional
    public void delete(Long id){
        themeRepository.deleteById(id);
    }
}
