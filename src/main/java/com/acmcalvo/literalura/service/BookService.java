package com.acmcalvo.literalura.service;

import com.acmcalvo.literalura.model.Book;
import com.acmcalvo.literalura.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class BookService {

    @Autowired
    private GutendexClient gutendexClient;

    @Autowired
    private BookRepository bookRepository;

    public Book searchAndSaveBookByTitle(String title) throws IOException, InterruptedException {
        Book book = gutendexClient.searchBookByTitle(title);
        if (book != null) {
            bookRepository.save(book);
        }
        return book;
    }

    public List<Book> listAllBooks() {
        return bookRepository.findAll();
    }

    public List<Book> listBooksByLanguage(String language) {
        return bookRepository.findByLanguage(language);
    }
}
