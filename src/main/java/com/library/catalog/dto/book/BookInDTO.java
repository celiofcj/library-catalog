package com.library.catalog.dto.book;

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
public class BookInDTO {
    private String title;
    private Integer publishYear;
    private String publisher;
    private List<Long> themes;
    private List<Long> writers;
}
