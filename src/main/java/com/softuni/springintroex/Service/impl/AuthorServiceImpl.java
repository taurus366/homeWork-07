package com.softuni.springintroex.Service.impl;


import com.softuni.springintroex.Service.AuthorService;
import com.softuni.springintroex.constants.GlobalConstants;
import com.softuni.springintroex.dao.AuthorRepository;
import com.softuni.springintroex.entities.Author;
import com.softuni.springintroex.utils.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Transactional
@Service
public class AuthorServiceImpl implements AuthorService {

    @Autowired
    private final AuthorRepository authorRepository;

    @Autowired
    private final FileUtil fileUtil;

    public AuthorServiceImpl(AuthorRepository authorRepository, FileUtil fileUtil) {
        this.authorRepository = authorRepository;
        this.fileUtil = fileUtil;
    }

    @Override
    public void seedAuthor() throws IOException {
        if (authorRepository.count() != 0){
            return;
        }
        String[] fileContent = this.fileUtil
                .readFileContent(GlobalConstants.AUTHORS_FILE_PATH);


        Arrays.stream(fileContent)
                .forEach(r -> {
                    String[] params = r.split("\\s+");

                    Author author = new Author(params[0],params[1]);

                    this.authorRepository.saveAndFlush(author);
                } );
    }

    @Override
    public int getAllAuthorsCount() {
        return (int) this.authorRepository.count();
    }

    @Override
    public Author findAuthorById(Long id) {
        return this.authorRepository.getOne(id);
    }

    @Override
    public List<Author> findAuthorByCountOfBook() {
        return this.authorRepository.findAuthorByCountOfBook();
    }
}
