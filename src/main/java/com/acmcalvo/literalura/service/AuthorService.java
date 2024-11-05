package com.acmcalvo.literalura.service;

import com.acmcalvo.literalura.dto.AuthorDTO;
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

    public Author findOrCreateAuthorByDTO(AuthorDTO authorDTO) {
        Optional<Author> existingAuthor = authorRepository.findByName(authorDTO.getName());
        return existingAuthor.orElseGet(() -> {
            Author newAuthor = new Author(authorDTO.getName(), authorDTO.getBirthYear(), authorDTO.getDeathYear());
            return authorRepository.save(newAuthor);
        });
    }

    // Método para buscar autores vivos en un año específico
    public List<Author> findAuthorsAliveInYear(int year) {
        return authorRepository.findAuthorsAliveInYear(year);
    }
}
