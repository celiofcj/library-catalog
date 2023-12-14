package com.library.catalog.dto;

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
public class BookOutDTO {
    private Long id;
    private String title;
    private Integer publishYear;
    private String publisher;
    private List<ThemeOutDTO> themes;
    private List<WriterOutDTO> writers;
}
