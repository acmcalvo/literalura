package com.acmcalvo.literalura.service;

import com.acmcalvo.literalura.model.Book;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Component
public class GutendexClient {

    private static final String BASE_URL = "https://gutendex.com/books/";
    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;

    public GutendexClient() {
        this.httpClient = HttpClient.newHttpClient();
        this.objectMapper = new ObjectMapper();
    }

    public Book searchBookByTitle(String title) throws IOException, InterruptedException {
        String url = BASE_URL + "?search=" + title;

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200) {
            JsonNode rootNode = objectMapper.readTree(response.body());
            JsonNode bookNode = rootNode.path("results").path(0);  // Obtenemos el primer resultado

            // Mapear el primer resultado a un objeto Book
            String bookTitle = bookNode.path("title").asText();
            String author = bookNode.path("authors").get(0).path("name").asText();
            String language = bookNode.path("languages").get(0).asText();
            int downloadCount = bookNode.path("download_count").asInt();

            return new Book(bookTitle, author, language, downloadCount);
        }

        return null;
    }
}
