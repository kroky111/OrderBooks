package com.gonyaevaa.orderBook.rest;

import com.gonyaevaa.orderBook.mapper.BookMapper;
import com.gonyaevaa.orderBook.model.Book;
import com.gonyaevaa.orderBook.model.dto.BookDto;
import com.gonyaevaa.orderBook.model.resp.BookResponse;
import com.gonyaevaa.orderBook.service.BookService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
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
@Api(tags = "book", value = "Operations pertaining to books")
public class BookRestController {

    private final BookService bookService;

    private final BookMapper bookMapper;

    @Autowired
    public BookRestController(BookService bookService, BookMapper bookMapper) {
        this.bookService = bookService;
        this.bookMapper = bookMapper;
    }

    @ApiOperation(value = "View a list of available books", response = BookResponse.class)
    @GetMapping(value = "")
    public List<BookDto> listAllBooks() {
        log.info("Show all books");

        return this.bookMapper
                .toBookDtos(this.bookService.findAllBooks());
    }

    @ApiOperation(value = "Find book by ID", notes = "Returns one book", response = BookResponse.class)
    @GetMapping("{bookId}")
    public BookDto getBook(@ApiParam(value = "Id of the book to be obtained", required = true)
                           @PathVariable("bookId") Long bookId) {
        log.info("Get book with id {}", bookId);

        Book currentBook = this.bookService.findBookById(bookId);

        return this.bookMapper.toBookDto(currentBook);
    }

    @ApiOperation(value = "Create a new book", response = BookResponse.class)
    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public BookDto createBook(@ApiParam(value = "Book object that needs to be added", name = "Book", required = true)
                              @Valid @RequestBody BookDto bookDto) {
        log.info("Create book : {}", bookDto);

        Book newBook = this.bookMapper.toBook(bookDto);
        this.bookService.saveBook(newBook);

        return this.bookMapper.toBookDto(newBook);
    }

    @ApiOperation(value = "Update an existing book", response = BookResponse.class)
    @PutMapping("{bookId}")
    public BookDto updateBook(@ApiParam(value = "ID of book that needs to be updated", required = true)
                              @PathVariable("bookId") Long bookId,
                              @ApiParam(value = "Updated book object", name = "Book", required = true)
                              @Valid @RequestBody BookDto bookDto) {
        log.info("Update book with id {}", bookId);

        Book currentBook = this.bookService.findBookById(bookId);

        this.bookMapper.bookDtoIntoBook(bookDto, currentBook);
        this.bookService.saveBook(currentBook);

        return this.bookMapper.toBookDto(currentBook);
    }

    @ApiOperation(value = "Delete a book")
    @DeleteMapping("{bookId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBook(@ApiParam(value = "Book id to delete", required = true)
                           @PathVariable("bookId") Long bookId) {
        log.info("Delete book with id {}", bookId);

        this.bookService.findBookById(bookId);
        this.bookService.deleteBook(bookId);
    }
}
