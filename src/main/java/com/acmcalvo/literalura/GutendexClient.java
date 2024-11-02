package com.acmcalvo.literalura;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class GutendexClient {

    private static final String BASE_URL = "https://gutendex.com/books";

    public static void main(String[] args) {
        GutendexClient client = new GutendexClient();
        client.searchBooksByTitle("Pride and Prejudice");
    }

    public void searchBooksByTitle(String title) {
        HttpClient client = HttpClient.newHttpClient();
        String url = BASE_URL + "?search=" + title.replace(" ", "%20");

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Accept", "application/json")
                .GET()
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            processResponse(response);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void processResponse(HttpResponse<String> response) {
        // Verificar código de estado
        int statusCode = response.statusCode();
        System.out.println("Código de estado: " + statusCode);

        if (statusCode == 200) {
            // Obtener y mostrar encabezados
            System.out.println("Encabezados:");
            for (Map.Entry<String, List<String>> header : response.headers().map().entrySet()) {
                System.out.println(header.getKey() + ": " + String.join(", ", header.getValue()));
            }

            // Procesar el cuerpo de la respuesta (JSON)
            String responseBody = response.body();
            System.out.println("Cuerpo de la respuesta:");

            try {
                // Usar Jackson para parsear JSON y extraer datos
                ObjectMapper mapper = new ObjectMapper();
                JsonNode root = mapper.readTree(responseBody);

                // Extraer información significativa (ejemplo: títulos de libros)
                JsonNode books = root.path("results");
                for (JsonNode book : books) {
                    String title = book.path("title").asText();
                    String author = book.path("authors").get(0).path("name").asText();
                    System.out.println("Título: " + title + ", Autor: " + author);
                }
            } catch (IOException e) {
                System.out.println("Error al procesar el JSON: " + e.getMessage());
            }
        } else {
            System.out.println("Solicitud fallida. Código de estado: " + statusCode);
        }
    }
}
