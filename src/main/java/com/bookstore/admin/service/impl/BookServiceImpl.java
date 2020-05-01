package com.bookstore.admin.service.impl;

import com.bookstore.admin.domain.Book;
import com.bookstore.admin.repository.BookRepository;
import com.bookstore.admin.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    BookRepository bookRepository;

    public Book save(Book book) {
        bookRepository.save(book);
        return book;
    }

    @Override
    public List<Book> finAll() {
        return bookRepository.findAll();
    }

    @Override
    public Book findBookById(Long id) {
        return bookRepository.getOne(id);
    }
}
