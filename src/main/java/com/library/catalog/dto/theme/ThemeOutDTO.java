package com.library.catalog.dto.theme;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Component
public class ThemeOutDTO {
    private Long id;
    private String name;
    private String description;
    private List<Long> books;
}
