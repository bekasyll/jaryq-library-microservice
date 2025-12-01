package com.bekassyl.books.mapper;

import com.bekassyl.books.dto.BookDto;
import com.bekassyl.books.entity.Book;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BookMapper {
    Book toEntity(BookDto bookDto);

    BookDto toDto(Book book);
}
