package org.gmdev.api.model.bookstore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class BookGroupByReviewDto {

    private Long id;
    private String title;
    private String isbn;
    private Long reviews;
}
