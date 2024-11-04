package com.acmcalvo.literalura.service;

import com.acmcalvo.literalura.model.Author;
import com.acmcalvo.literalura.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AuthorService {

    private final AuthorRepository authorRepository;

    @Autowired
    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public Author findOrCreateAuthorByName(String name, Integer birthYear) {
        Optional<Author> existingAuthor = authorRepository.findByName(name);
        return existingAuthor.orElseGet(() -> {
            Author newAuthor = new Author(name, birthYear, null); // Suponiendo que la fecha de fallecimiento es null
            return authorRepository.save(newAuthor);
        });
    }

    public List<Author> findAuthorsAliveInYear(int year) {
        return authorRepository.findAuthorsAliveInYear(year);
    }
}
