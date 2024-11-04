package com.acmcalvo.literalura.principal;

import com.acmcalvo.literalura.model.Author;
import com.acmcalvo.literalura.model.Book;
import com.acmcalvo.literalura.service.AuthorService;
import com.acmcalvo.literalura.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Scanner;

@Component
public class Principal {

    private final BookService bookService;
    private final AuthorService authorService; // Añadir AuthorService

    @Autowired
    public Principal(BookService bookService, AuthorService authorService) { // Constructor con AuthorService
        this.bookService = bookService;
        this.authorService = authorService; // Inicializar AuthorService
    }

    public void mostrarMenu() {
        Scanner scanner = new Scanner(System.in);
        int opcion;

        do {
            System.out.println("Menú de opciones:");
            System.out.println("1. Buscar libro por título");
            System.out.println("2. Lista todos los libros registrados");
            System.out.println("3. Lista de autores registrados");
            System.out.println("4. Lista libros por idioma");
            System.out.println("5. Lista de autores vivos en un año específico");
            System.out.println("0. Salir");
            System.out.print("Seleccione una opción: ");
            opcion = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (opcion) {
                case 1 -> {
                    System.out.print("Ingrese el título del libro: ");
                    String title = scanner.nextLine();
                    try {
                        Book book = bookService.searchAndSaveBookByTitle(title);
                        if (book != null) {
                            System.out.println("Libro encontrado y guardado: " + book);
                        } else {
                            System.out.println("Libro no encontrado.");
                        }
                    } catch (Exception e) {
                        System.out.println("Error en la búsqueda: " + e.getMessage());
                    }
                }
                case 2 -> {
                    List<Book> books = bookService.listAllBooks();
                    System.out.println("Listado de todos los libros:");
                    books.forEach(System.out::println);
                }
                case 3 -> {
                    List<Author> authors = bookService.listAllAuthors();
                    System.out.println("Lista de autores registrados:");
                    if (authors.isEmpty()) {
                        System.out.println("No se encontraron autores registrados.");
                    } else {
                        authors.forEach(author -> System.out.println(author.getName()));
                    }
                }
                case 4 -> {
                    System.out.print("Ingrese el idioma \n es -espańol, \n en - Inglés, \n fr -Frances,\n pt - Portugues \n");
                    String language = scanner.nextLine().trim();
                    List<Book> booksByLanguage = bookService.listBooksByLanguage(language);
                    System.out.println("Libros en " + language + ":");
                    if (booksByLanguage.isEmpty()) {
                        System.out.println("No se encontraron libros en el idioma " + language + ".");
                    } else {
                        booksByLanguage.forEach(System.out::println);
                    }
                }
                case 5 -> {
                    System.out.print("Ingrese el año: ");
                    int year = scanner.nextInt();
                    List<Author> authorsAlive = authorService.findAuthorsAliveInYear(year);
                    System.out.println("Autores vivos en " + year + ":");
                    if (authorsAlive.isEmpty()) {
                        System.out.println("No se encontraron autores vivos en el año " + year + ".");
                    } else {
                        authorsAlive.forEach(author -> System.out.println(author.getName()));
                    }
                }
                case 0 -> System.out.println("Saliendo...");
                default -> System.out.println("Opción no válida.");
            }
        } while (opcion != 0);

        scanner.close();
    }
}