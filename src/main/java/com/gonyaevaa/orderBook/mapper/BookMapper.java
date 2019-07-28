package com.gonyaevaa.orderBook.mapper;

import com.gonyaevaa.orderBook.config.MapConfig;
import com.gonyaevaa.orderBook.model.Book;
import com.gonyaevaa.orderBook.model.dto.BookDto;
import org.mapstruct.*;

import java.util.List;

@Mapper(config = MapConfig.class)
public interface BookMapper {

    @Mapping(target = "orderDtoList", ignore = true)
    BookDto toBookDto(Book book);

    List<BookDto> toBookDtos(List<Book> books);

    @InheritInverseConfiguration
    @Mapping(target = "bookId", ignore = true)
    @Mapping(target = "orders", ignore = true)
    Book toBook(BookDto bookDto);

    @InheritConfiguration
    void bookDtoIntoBook(BookDto bookDto, @MappingTarget Book book);
}