package org.gmdev.springbootdemo.model.mapper.entitymapper;

import org.gmdev.springbootdemo.model.dto.ReviewDto;
import org.gmdev.springbootdemo.model.entity.Review;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", uses = { BookMapper.class })
public interface ReviewMapper {

    ReviewMapper INSTANCE = Mappers.getMapper(ReviewMapper.class);

    @Mapping(target = "bookId", source = "book.id")
    ReviewDto toDto(Review review);

    @Mapping(target = "book", source = "bookId")
    Review toEntity(ReviewDto reviewDto);
}
