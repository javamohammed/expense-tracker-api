package com.mido.expensetracker.service;

import java.util.List;

import com.mido.expensetracker.entity.Category;
import com.mido.expensetracker.exception.EtBadRequestException;
import com.mido.expensetracker.exception.EtResourceNotFoundException;

public interface CategoryService {

	 List<Category> fetchAllCategories(Integer userId);

    Category fetchCategoryById(Integer userId, Integer categoryId) throws EtResourceNotFoundException;

    Category addCategory(Integer userId, String title, String description) throws EtBadRequestException;

    void updateCategory(Integer userId, Integer categoryId, Category category) throws EtBadRequestException;

    void removeCategoryWithAllTransactions(Integer userId, Integer categoryId) throws EtResourceNotFoundException;
}
