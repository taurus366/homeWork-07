package com.softuni.springintroex.Service;



import com.softuni.springintroex.entities.Author;

import java.io.IOException;
import java.util.List;

public interface AuthorService {
    void seedAuthor() throws IOException;

    int getAllAuthorsCount();

    Author findAuthorById(Long id);

    List<Author> findAuthorByCountOfBook();
}
