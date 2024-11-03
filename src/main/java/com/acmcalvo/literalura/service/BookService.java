package com.acmcalvo.literalura.service;

import com.acmcalvo.literalura.model.Book;
import com.acmcalvo.literalura.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class BookService {

    private final BookRepository bookRepository;
    private final GutendexClient gutendexClient;

    @Autowired
    public BookService(BookRepository bookRepository, GutendexClient gutendexClient) {
        this.bookRepository = bookRepository;
        this.gutendexClient = gutendexClient;
    }

    public Book searchAndSaveBookByTitle(String title) throws IOException, InterruptedException {
        Book book = gutendexClient.searchBookByTitle(title);
        if (book != null) {
            bookRepository.save(book);
        } else {
            System.out.println("No se pudo guardar el libro porque no se encontró.");
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
