package com.acmcalvo.literalura.service;

import com.acmcalvo.literalura.model.Author;
import com.acmcalvo.literalura.model.Book;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

@Component
public class GutendexClient {

    private static final String BASE_URL = "https://gutendex.com/books/"; // Definición directa de la URL
    private final ObjectMapper objectMapper;

    public GutendexClient() {
        this.objectMapper = new ObjectMapper();
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
            JsonNode bookNode = rootNode.path("results").path(0); // Obtenemos el primer resultado

            if (bookNode.isMissingNode()) {
                return null; // No hay libros disponibles
            }

            // Mapea el autor a un objeto Author
            String bookTitle = bookNode.path("title").asText();
            String authorName = bookNode.path("authors").get(0).path("name").asText();
            Author author = new Author(authorName); // Crea un objeto Author
            String language = bookNode.path("languages").get(0).asText();
            int downloadCount = bookNode.path("download_count").asInt();

            return new Book(bookTitle, author, language, downloadCount); // Usa el objeto Author aquí
        }
    }
}
