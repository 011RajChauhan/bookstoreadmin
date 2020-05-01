package com.bookstore.admin.service;

import com.bookstore.admin.domain.Book;

import java.util.List;
import java.util.Optional;

public interface BookService {

    Book save(Book book);

    List<Book> finAll();

    Book findBookById(Long id);
}
