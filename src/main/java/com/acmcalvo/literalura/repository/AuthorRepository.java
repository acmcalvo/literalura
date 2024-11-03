package com.acmcalvo.literalura.repository;

import com.acmcalvo.literalura.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {
    // Método para encontrar un autor por su nombre
    Author findByName(String name);
}
