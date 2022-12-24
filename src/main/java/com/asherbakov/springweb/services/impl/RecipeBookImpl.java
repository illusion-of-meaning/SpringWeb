package com.asherbakov.springweb.services.impl;

import com.asherbakov.springweb.models.Ingredient;
import com.asherbakov.springweb.models.Recipe;
import com.asherbakov.springweb.services.IngredientsService;
import com.asherbakov.springweb.services.RecipeBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class RecipeBookImpl implements RecipeBookService {
    private static Long id = 0L;
    private static final Map<Long, Recipe> recipes = new HashMap<>();

    @Autowired
    IngredientsService ingredientsService;

    @Override
    public void addRecipe(Recipe recipe) {
        if (!recipes.containsValue(recipe)) {
            recipes.put(id++, recipe);
        }
    }

    @Override
    public void addRecipe(String name, int cookingTime, Long[] ingId, String[] steps) {
        List<String> stepList;
        Map<Long, Ingredient> ingredients = new HashMap<>();

        if (ingId.length > 0) {
            long id = 0L;
            for (Long i : ingId) {
                ingredients.put(id++, ingredientsService.getIngredient(i));
            }
        }

        if (steps.length > 0) {
            stepList = new ArrayList<>(Arrays.stream(steps).toList());
        } else {
            stepList = new ArrayList<>();
        }

        recipes.put(id++, new Recipe(name, cookingTime, ingredients, stepList));
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
