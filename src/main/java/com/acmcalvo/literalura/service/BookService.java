package com.acmcalvo.literalura.service;

import com.acmcalvo.literalura.model.Author;
import com.acmcalvo.literalura.model.Book;
import com.acmcalvo.literalura.repository.AuthorRepository;
import com.acmcalvo.literalura.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

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
            Author author = authorRepository.findByName(book.getAuthor().getName());
            if (author == null) {
                // Si no existe, lo crea y guarda
                author = new Author(book.getAuthor().getName());
                authorRepository.save(author);
            }
            book.setAuthor(author); // Asigna el objeto Author al libro

            // Aquí, asegúrate de que el método 'save' del repositorio esté funcionando correctamente
            bookRepository.save(book); // Guarda el libro
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
