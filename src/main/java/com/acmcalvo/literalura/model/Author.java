package com.acmcalvo.literalura.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true) // Ignora campos no deseados en la respuesta JSON
public class Author {

    @JsonAlias("name") // Mapea el campo "name" de JSON a "name" en Java
    private String name;

    // Constructor
    public Author() {}

    // Getters y Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // toString para facilitar la impresión de datos
    @Override
    public String toString() {
        return name;
    }
}
