package com.acmcalvo.literalura.service;

import com.acmcalvo.literalura.model.Author;
import com.acmcalvo.literalura.model.Book;
import com.acmcalvo.literalura.repository.AuthorRepository;
import com.acmcalvo.literalura.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository; // Declaración del AuthorRepository
    private final GutendexClient gutendexClient;

    @Autowired
    public BookService(BookRepository bookRepository, AuthorRepository authorRepository, GutendexClient gutendexClient) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.gutendexClient = gutendexClient;
    }

    public Book searchAndSaveBookByTitle(String title) throws IOException, InterruptedException {
        Book book = gutendexClient.searchBookByTitle(title);
        if (book != null) {
            // Busca si el autor ya existe
            Optional<Author> existingAuthor = authorRepository.findByName(book.getAuthor().getName());
            Author author = existingAuthor.orElseGet(() -> {
                // Si no existe, lo crea y guarda con birthYear y deathYear como null
                Author newAuthor = new Author(book.getAuthor().getName(), null, null);
                return authorRepository.save(newAuthor);
            });

            book.setAuthor(author); // Asigna el objeto Author al libro

            // Guarda el libro
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

    public List<Author> listAllAuthors() {
        return authorRepository.findAll(); // Método para listar todos los autores
    }
}
