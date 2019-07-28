package com.gonyaevaa.orderBook.rest;

import com.gonyaevaa.orderBook.mapper.BookMapper;
import com.gonyaevaa.orderBook.model.Book;
import com.gonyaevaa.orderBook.model.dto.BookDto;
import com.gonyaevaa.orderBook.service.BookService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1/books", produces = MediaType.APPLICATION_JSON_VALUE)
public class BookRestController {

    private final BookService bookService;

    private final BookMapper bookMapper;

    @Autowired
    public BookRestController(BookService bookService, BookMapper bookMapper) {
        this.bookService = bookService;
        this.bookMapper = bookMapper;
    }

    @GetMapping(value = "")
    public List<BookDto> listAllBooks() {
        log.info("Show all books");

        return this.bookMapper
                .toBookDtos(this.bookService.findAllBooks());
    }

    @GetMapping("{bookId}")
    public BookDto getBook(@PathVariable("bookId") Long bookId) {
        log.info("Get book with id {}", bookId);

        Book currentBook = this.bookService.findBookById(bookId);

        return this.bookMapper.toBookDto(currentBook);
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public BookDto createBook(@Valid @RequestBody BookDto bookDto) {
        log.info("Create book : {}", bookDto);

        Book newBook = this.bookMapper.toBook(bookDto);
        this.bookService.saveBook(newBook);

        return this.bookMapper.toBookDto(newBook);
    }

    @PutMapping("{bookId}")
    public BookDto updateBook(@PathVariable("bookId") Long bookId,
                              @Valid @RequestBody BookDto bookDto) {
        log.info("Update book with id {}", bookId);

        Book currentBook = this.bookService.findBookById(bookId);

        this.bookMapper.bookDtoIntoBook(bookDto, currentBook);
        this.bookService.saveBook(currentBook);

        return this.bookMapper.toBookDto(currentBook);
    }

    @DeleteMapping("{bookId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBook(@PathVariable("bookId") Long bookId) {
        log.info("Delete book with id {}", bookId);

        this.bookService.findBookById(bookId);
        this.bookService.deleteBook(bookId);
    }
}
