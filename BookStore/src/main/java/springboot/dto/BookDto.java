package springboot.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookDto {

    private int id;

    @NotBlank(message="Title is required")
    private String title;

    @NotBlank(message="Author is required")
    private String author;
}
