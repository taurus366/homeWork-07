package com.softuni.springintroex.init;

import com.softuni.springintroex.Service.AuthorService;
import com.softuni.springintroex.Service.BookService;
import com.softuni.springintroex.Service.CategoryService;
import com.softuni.springintroex.entities.AgeRestriction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class CommandLine implements CommandLineRunner {

    @Autowired
    private final CategoryService categoryService;

    @Autowired
    private final AuthorService authorService;

    @Autowired
    private final BookService bookService;

    public CommandLine(CategoryService categoryService, AuthorService authorService, BookService bookService) {
        this.categoryService = categoryService;
        this.authorService = authorService;
        this.bookService = bookService;
    }

    @Override
    public void run(String... args) throws Exception {

//        categoryService.seedCategories();
//        authorService.seedAuthor();
//        bookService.seedBooks();

        //1.	Books Titles by Age Restriction
//        AgeRestriction ageRestriction = AgeRestriction.valueOf("teEn".toUpperCase());
//        this.bookService.findBooksTitleByAgeRestriction(ageRestriction)
//                .forEach(s -> System.out.println(s.getTitle()));


        // 2. Golden Books
//        this.bookService.findGoldenBooksByLessThan5000()
//                .forEach(s -> System.out.println(s.getTitle()));

        //3. Books by Price
//        this.bookService.findBooksByPriceLowerThan5AndHigherThan40()
//                .forEach(s -> System.out.println(s.getTitle()+ " " + s.getPrice()));

        //4. Remove Books
//        System.out.println(this.bookService.removeBooksByCopiesLessThanNumber(27275));
    }
}
