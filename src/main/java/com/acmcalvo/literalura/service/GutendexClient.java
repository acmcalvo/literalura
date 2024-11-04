package com.acmcalvo.literalura.service;

import com.acmcalvo.literalura.model.Author;
import com.acmcalvo.literalura.model.Book;
import com.acmcalvo.literalura.service.AuthorService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
//import com.acmcalvo.literalura.service.AuthorService; // Asegúrate de importar tu servicio de autor
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

@Component
public class GutendexClient {
    private static final String BASE_URL = "https://gutendex.com/books/";
    private final ObjectMapper objectMapper;
    private final AuthorService authorService; // Inyectar AuthorService

    @Autowired
    public GutendexClient(AuthorService authorService) {
        this.objectMapper = new ObjectMapper();
        this.authorService = authorService; // Inicializar el AuthorService
    }

    public Book searchBookByTitle(String title) throws IOException, InterruptedException {
        String urlString = BASE_URL + "?search=" + title;
        URL url = new URL(urlString);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        try (Scanner scanner = new Scanner(connection.getInputStream())) {
            StringBuilder response = new StringBuilder();
            while (scanner.hasNext()) {
                response.append(scanner.nextLine());
            }

            JsonNode rootNode = objectMapper.readTree(response.toString());
            JsonNode bookNode = rootNode.path("results").path(0);

            if (bookNode.isMissingNode()) {
                return null;
            }

            String bookTitle = bookNode.path("title").asText();
            String authorName = bookNode.path("authors").get(0).path("name").asText();
            Author author = authorService.findOrCreateAuthor(authorName); // Busca o crea el autor
            String language = bookNode.path("languages").get(0).asText();
            int downloadCount = bookNode.path("download_count").asInt();

            return new Book(bookTitle, author, language, downloadCount);
        }
    }
}
