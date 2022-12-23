package com.asherbakov.springweb.services;

import com.asherbakov.springweb.models.Recipe;

import java.util.Map;

public interface RecipeBookService {
    void addRecipe(Recipe recipe);
    Map<Long, Recipe> getAllRecipes();
    Recipe getRecipe(Long i);

    boolean removeRecipe(Long i);

    boolean editRecipe(Long i, Recipe recipe);
}
