package org.gmdev.springbootdemo.model.mapper.entitymapper;

import org.gmdev.springbootdemo.model.dto.AuthorDto;
import org.gmdev.springbootdemo.model.entity.Author;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", uses = { BookMapper.class })
public interface AuthorMapper {

    AuthorMapper INSTANCE = Mappers.getMapper(AuthorMapper.class);

    @Mapping(target = "books", ignore = true)
    @Named(value = "toAuthorDtoNoBooks")
    AuthorDto toDtoNoBooks(Author author);

    @Mapping(target = "books", qualifiedByName = "toBookDtoPlusDetail")
    @Named(value = "toAuthorDto")
    AuthorDto toDto(Author author);

    Author toEntity(AuthorDto authorDto);

}
