package com.asherbakov.springweb.services.impl;

import com.asherbakov.springweb.models.Recipe;
import com.asherbakov.springweb.services.RecipeBookService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class RecipeBookImpl implements RecipeBookService {
    private static Integer id = 0;
    private static final Map<Integer, Recipe> recipes = new HashMap<>();
    @Override
    public void addRecipe(Recipe recipe) {
        if (!recipes.containsValue(recipe)) {
            recipes.put(id++, recipe);
        }
    }

    @Override
    public Map<Integer, Recipe> getAllRecipes() {
        return recipes;
    }

    @Override
    public Recipe getRecipe(Integer i) {
        return recipes.getOrDefault(i, null);
    }
}
