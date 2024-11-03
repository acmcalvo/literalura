package com.acmcalvo.literalura.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true) // Ignorar propiedades desconocidas
public class Book {

    private String title;
    private Author author; // Cambia esto a Author
    private String language;
    private int downloadCount;

    // Constructor que recibe todos los atributos
    public Book(String title, Author author, String language, int downloadCount) {
        this.title = title;
        this.author = author; // Ahora es de tipo Author
        this.language = language;
        this.downloadCount = downloadCount;
    }

    // Getters
    public String getTitle() {
        return title;
    }

    public Author getAuthor() { // Cambia el tipo de retorno a Author
        return author;
    }

    public String getLanguage() {
        return language;
    }

    public int getDownloadCount() {
        return downloadCount;
    }

    // Setters
    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(Author author) { // Cambia el parámetro a Author
        this.author = author;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public void setDownloadCount(int downloadCount) {
        this.downloadCount = downloadCount;
    }

    // toString para una representación legible del objeto
    @Override
    public String toString() {
        return "Book{" +
                "title='" + title + '\'' +
                ", author=" + (author != null ? author.getName() : "N/A") + // Usa el nombre del autor
                ", language='" + language + '\'' +
                ", downloadCount=" + downloadCount +
                '}';
    }
}
