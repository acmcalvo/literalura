package com.acmcalvo.literalura.service;

import com.acmcalvo.literalura.model.Book;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GutendexResponse {

    private List<Book> results;

    // Getters y Setters
    public List<Book> getResults() {
        return results;
    }

    public void setResults(List<Book> results) {
        this.results = results;
    }

    @Override
    public String toString() {
        return "GutendexResponse{" +
                "results=" + results +
                '}';
    }
}
