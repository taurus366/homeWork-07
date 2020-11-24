package com.softuni.springintroex.Service;

import com.softuni.springintroex.entities.AgeRestriction;
import com.softuni.springintroex.entities.Book;
import com.softuni.springintroex.utils.FileUtil;
import org.springframework.data.jpa.repository.Query;

import java.io.IOException;
import java.util.List;

public interface BookService {

    void seedBooks() throws IOException;


    List<Book> findBooksTitleByAgeRestriction(AgeRestriction ageRestriction);

    List<Book> findGoldenBooksByLessThan5000();

    List<Book> findBooksByPriceLowerThan5AndHigherThan40();

    int removeBooksByCopiesLessThanNumber(int copies);
}
