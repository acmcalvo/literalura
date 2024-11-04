package com.acmcalvo.literalura.service;

import com.acmcalvo.literalura.model.Author;
import com.acmcalvo.literalura.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthorService {
    @Autowired
    private AuthorRepository authorRepository;

    public Author findOrCreateAuthor(String name) {
        Author author = authorRepository.findByName(name);
        if (author == null) {
            author = new Author(name);
            authorRepository.save(author);
        }
        return author;
    }
}
