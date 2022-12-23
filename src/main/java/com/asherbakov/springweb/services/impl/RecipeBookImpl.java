package com.asherbakov.springweb.services.impl;

import com.asherbakov.springweb.models.Recipe;
import com.asherbakov.springweb.services.RecipeBookService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class RecipeBookImpl implements RecipeBookService {
    private static Long id = 0L;
    private static final Map<Long, Recipe> recipes = new HashMap<>();

    @Override
    public void addRecipe(Recipe recipe) {
        if (!recipes.containsValue(recipe)) {
            recipes.put(id++, recipe);
        }
    }

    @Override
    public Map<Long, Recipe> getAllRecipes() {
        return recipes;
    }

    @Override
    public Recipe getRecipe(Long i) {
        return recipes.getOrDefault(i, null);
    }

    @Override
    public boolean removeRecipe(Long i) {
        if (recipes.containsKey(i)) {
            recipes.remove(i);
            return true;
        }
        return false;
    }

    @Override
    public boolean editRecipe(Long i, Recipe recipe) {
        if (recipes.containsKey(i)) {
            recipes.replace(i, recipe);
            return true;
        }
        return false;
    }
}
