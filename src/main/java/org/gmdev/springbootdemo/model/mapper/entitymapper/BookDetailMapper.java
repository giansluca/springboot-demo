package org.gmdev.springbootdemo.model.mapper.entitymapper;

import org.gmdev.springbootdemo.model.dto.BookDetailDto;
import org.gmdev.springbootdemo.model.entity.BookDetail;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface BookDetailMapper {

    BookDetailMapper INSTANCE = Mappers.getMapper(BookDetailMapper.class);

    BookDetailDto toDto(BookDetail bookDetail);

    BookDetail toEntity(BookDetailDto bookDetailDto);
}
