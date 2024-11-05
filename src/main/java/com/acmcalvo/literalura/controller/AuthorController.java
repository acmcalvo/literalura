package com.acmcalvo.literalura.controller;

import com.acmcalvo.literalura.dto.AuthorDTO;
import com.acmcalvo.literalura.model.Author;
import com.acmcalvo.literalura.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/authors")
public class AuthorController {

    private final AuthorService authorService;

    @Autowired
    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @PostMapping
    public Author createAuthor(@RequestBody AuthorDTO authorDTO) {
        return authorService.findOrCreateAuthorByDTO(authorDTO);
    }
}
