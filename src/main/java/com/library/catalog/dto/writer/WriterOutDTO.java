package com.library.catalog.dto.writer;

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
public class WriterOutDTO {
    private Long id;
    private String name;
    private String bio;
    private List<Long> books;
}
