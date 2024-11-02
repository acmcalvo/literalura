package com.acmcalvo.literalura.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Book {

    @JsonAlias("title")
    private String title;

    @JsonAlias("authors")
    private List<Author> authors;

    @JsonAlias("languages")
    private List<String> languages;

    @JsonAlias("download_count")
    private int downloadCount;

    // Constructor
    public Book() {}

    // Getters y Setters
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(List<Author> authors) {
        this.authors = authors;
    }

    public List<String> getLanguages() {
        return languages;
    }

    public void setLanguages(List<String> languages) {
        this.languages = languages;
    }

    public int getDownloadCount() {
        return downloadCount;
    }

    public void setDownloadCount(int downloadCount) {
        this.downloadCount = downloadCount;
    }

    // toString para facilitar la impresión de datos
    @Override
    public String toString() {
        return "Book{" +
                "title='" + title + '\'' +
                ", authors=" + authors +
                ", languages=" + languages +
                ", downloadCount=" + downloadCount +
                '}';
    }
}
