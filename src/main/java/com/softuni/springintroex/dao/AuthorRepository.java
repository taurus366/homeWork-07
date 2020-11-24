package com.softuni.springintroex.dao;

import com.softuni.springintroex.entities.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuthorRepository extends JpaRepository<Author,Long> {
    @Query("SELECT a FROM Author AS a ORDER BY a.books.size DESC")
    List<Author> findAuthorByCountOfBook();
}
