package com.acmcalvo.literalura.service;

import com.acmcalvo.literalura.model.Book;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
public class GutendexClient {

    private static final String BASE_URL = "https://gutendex.com/books";
    private final HttpClient client = HttpClient.newHttpClient();
    private final ObjectMapper objectMapper = new ObjectMapper();

    public Book searchBookByTitle(String title) throws IOException, InterruptedException {
        String url = BASE_URL + "?search=" + title;
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return parseBookResponse(response.body());
    }

    private Book parseBookResponse(String responseBody) throws IOException {
        JsonNode root = objectMapper.readTree(responseBody);
        JsonNode firstBook = root.path("results").get(0);

        if (firstBook == null) {
            return null; // No books found
        }

        Book book = new Book();
        book.setTitle(firstBook.path("title").asText());
        book.setAuthor(firstBook.path("authors").get(0).path("name").asText());
        book.setLanguage(firstBook.path("languages").get(0).asText());
        book.setDownloadCount(firstBook.path("download_count").asInt());

        return book;
    }
}
