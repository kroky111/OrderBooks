package com.gonyaevaa.orderBook.service;

import com.gonyaevaa.orderBook.model.Book;

import java.util.List;

public interface BookService {

    List<Book> findAllBooks();

    void saveBook(Book book);

    void deleteBook(Long id);

    Book findBookById(Long id);
}
