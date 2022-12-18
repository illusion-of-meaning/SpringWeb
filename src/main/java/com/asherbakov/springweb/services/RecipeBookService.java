package com.asherbakov.springweb.services;

import com.asherbakov.springweb.models.Recipe;

import java.util.Map;

public interface RecipeBookService {
    void addRecipe(Recipe recipe);
    Map<Integer, Recipe> getAllRecipes();
    Recipe getRecipe(Integer i);
}
