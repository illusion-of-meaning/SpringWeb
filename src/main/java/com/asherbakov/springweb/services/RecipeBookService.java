package com.asherbakov.springweb.services;

import com.asherbakov.springweb.models.Recipe;

import java.util.Map;

public interface RecipeBookService {
    void addRecipe(Recipe recipe);

    void addRecipe(String name, int cookingTime, Long[] ingId, String[] steps);

    Map<Long, Recipe> getAllRecipes();

    Recipe getRecipe(Long i);

    boolean removeRecipe(Long i);

    boolean editRecipe(Long i, Recipe recipe);
}
