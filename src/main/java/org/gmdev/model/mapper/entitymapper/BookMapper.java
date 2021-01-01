package org.gmdev.model.mapper.entitymapper;

import org.gmdev.model.dto.BookDto;
import org.gmdev.model.entity.Book;
import org.gmdev.model.dto.BookGroupByReviewDto;
import org.gmdev.model.entity.BookGroupByReview;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.Set;

@Mapper(componentModel = "spring", uses = { ReviewMapper.class, AuthorMapper.class })
public interface BookMapper {

    BookMapper INSTANCE = Mappers.getMapper(BookMapper.class);

    @Mapping(target = "authors", qualifiedByName = "toAuthorDtoNoBooksCollection")
    @Named("toBookDto")
    BookDto toDto(Book book);

    @Mapping(ignore = true, target = "reviews")
    @Mapping(ignore = true, target = "bookDetail")
    @Mapping(ignore = true, target = "authors")
    @Named("toBookDtoLazy")
    BookDto toDtoLazy(Book book);

    @Mapping(ignore = true, target = "reviews")
    @Mapping(ignore = true, target = "authors")
    @Named("toBookDtoPlusDetail")
    BookDto toDtoPlusDetail(Book book);

    @Mapping(ignore = true, target = "reviews")
    @Mapping(ignore = true, target = "authors")
    @Named("toBookDtoPlusDetailCollection")
    Set<BookDto> toDtoPlusDetailCollection(Set<Book> book);

    Book toEntity(BookDto bookDto);

    default Book fromId(Long bookId) {
        Book book = new Book();
        book.setId(bookId);
        return book;
    }

    BookGroupByReviewDto toBookGroupByReviewDto1(BookGroupByReview bookGroupByReview);

}
