package com.softuni.springintroex.Service.impl;


import com.softuni.springintroex.Service.CategoryService;
import com.softuni.springintroex.constants.GlobalConstants;
import com.softuni.springintroex.dao.CategoryRepository;
import com.softuni.springintroex.entities.Category;
import com.softuni.springintroex.utils.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.Arrays;

@Transactional(propagation = Propagation.REQUIRED)
@Service
public class CategoryImpl implements CategoryService {

    @Autowired
    private final CategoryRepository categoryRepository;

    @Autowired
    private final FileUtil fileUtil;

    public CategoryImpl(CategoryRepository categoryRepository, FileUtil fileUtil) {
        this.categoryRepository = categoryRepository;
        this.fileUtil = fileUtil;
    }


    @Override
    public void seedCategories() throws IOException {

        if (this.categoryRepository.count() != 0){
            return;
        }

        String[] fileContent = this.fileUtil
                .readFileContent(GlobalConstants.CATEGORIES_FILE_PATH);


        Arrays.stream(fileContent)
                .forEach(r -> {
                    Category category = new Category(r);
                    this.categoryRepository.saveAndFlush(category);
                } );
    }

    @Override
    public Category getCategoryById(Long id) {
        return this.categoryRepository.getOne(id);
    }
}
