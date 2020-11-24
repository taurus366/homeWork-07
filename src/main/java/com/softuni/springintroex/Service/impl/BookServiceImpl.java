package com.softuni.springintroex.Service.impl;

import com.softuni.springintroex.Service.AuthorService;
import com.softuni.springintroex.Service.BookService;
import com.softuni.springintroex.Service.CategoryService;
import com.softuni.springintroex.constants.GlobalConstants;
import com.softuni.springintroex.dao.BookRepository;
import com.softuni.springintroex.entities.*;
import com.softuni.springintroex.utils.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Transactional()
@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private final BookRepository bookRepository;

    @Autowired
    private final FileUtil fileUtil;

    @Autowired
    private final AuthorService authorService;

    @Autowired
    private final CategoryService categoryService;

    public BookServiceImpl(BookRepository bookRepository, FileUtil fileUtil, AuthorService authorService, CategoryService categoryService) {
        this.bookRepository = bookRepository;
        this.fileUtil = fileUtil;
        this.authorService = authorService;
        this.categoryService = categoryService;
    }

    @Override
    public void seedBooks() throws IOException {

        String[] fileContent = this.fileUtil
                .readFileContent(GlobalConstants.BOOKS_FILE_PATH);

        Arrays.stream(fileContent)
                .forEach(r -> {
                    String[] splt = r.split("\\s+");

                    Author author = this.getRandomAuthor();


                    EditionType editionType = EditionType.values()[Integer.parseInt(splt[0])];

                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy");
                    LocalDate reLocalDate = LocalDate.parse(splt[1],formatter);

                    int copies = Integer.parseInt(splt[2]);

                    BigDecimal price = new BigDecimal(splt[3]);

                    AgeRestriction ageRestriction = AgeRestriction
                            .values()[Integer.parseInt(splt[4])];

                    String title = this.getTitle(splt);

                    Set<Category> categories = this.getRandomCategories();

                    Book book = new Book();
                    book.setAuthor(author);
                    book.setEditionType(editionType);
                    book.setReleaseDate(reLocalDate);
                    book.setPrice(price);
                    book.setAgeRestriction(ageRestriction);
                    book.setTitle(title);
                    book.setCategories(categories);
                    book.setCopies(copies);

                    this.bookRepository.saveAndFlush(book);


                });

    }

    @Override
    public List<Book> findBooksTitleByAgeRestriction(AgeRestriction ageRestriction) {
        return this.bookRepository.findByAgeRestriction(ageRestriction);
    }

    @Override
    public List<Book> findGoldenBooksByLessThan5000() {
        return this.bookRepository.findByEditionTypeAndCopiesLessThan(EditionType.GOLD,5000);
    }

    @Override
    public List<Book> findBooksByPriceLowerThan5AndHigherThan40() {
        return this.bookRepository.findByPriceLessThanOrPriceGreaterThan(new BigDecimal(5), new BigDecimal(40));
    }

    @Override
    public int removeBooksByCopiesLessThanNumber(int copies) {
        List<Book> byCopiesLessThan = this.bookRepository.findByCopiesLessThan(copies);
        byCopiesLessThan.forEach(b -> b.getCategories().clear());
        byCopiesLessThan.forEach(b -> b.setAuthor(null));


        return this.bookRepository.deleteByCopiesLessThan(copies);
    }

    private String getTitle(String[] splt) {
        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 5; i < splt.length ; i++) {
            stringBuilder.append(splt[i])
                    .append(" ");

        }
        return stringBuilder.toString().trim();
    }
    private Author getRandomAuthor() {
        Random random = new Random();

        int authorIndex = random.nextInt(this.authorService.getAllAuthorsCount()) + 1;

        return this.authorService.findAuthorById((long) authorIndex);
    }
    private Set<Category> getRandomCategories() {
        Set<Category> result = new HashSet<>();

        Random random = new Random();
        int bound = random.nextInt(3) + 1;

        int categoryId = random.nextInt(8) + 1;

        for (int i = 1; i <= bound; i++) {
            result.add(this.categoryService.getCategoryById((long) categoryId));
        }

        return result;
    }


}
