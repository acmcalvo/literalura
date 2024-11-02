package com.acmcalvo.literalura.principal;

import com.acmcalvo.literalura.model.Book;
import com.acmcalvo.literalura.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Scanner;

@Component
public class Principal {

    @Autowired
    private BookService bookService;

    public void mostrarMenu() {
        Scanner scanner = new Scanner(System.in);
        int opcion;

        do {
            System.out.println("Menú de opciones:");
            System.out.println("1. Buscar libro por título");
            System.out.println("2. Listar todos los libros");
            System.out.println("3. Listar libros por idioma");
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
                    System.out.print("Ingrese el idioma: ");
                    String language = scanner.nextLine();
                    List<Book> booksByLanguage = bookService.listBooksByLanguage(language);
                    System.out.println("Libros en " + language + ":");
                    booksByLanguage.forEach(System.out::println);
                }
                case 0 -> System.out.println("Saliendo...");
                default -> System.out.println("Opción no válida.");
            }
        } while (opcion != 0);

        scanner.close();
    }
}
