package com.asherbakov.springweb.services.impl;

import com.asherbakov.springweb.models.Ingredient;
import com.asherbakov.springweb.models.Recipe;
import com.asherbakov.springweb.services.FileService;
import com.asherbakov.springweb.services.IngredientsService;
import com.asherbakov.springweb.services.RecipeBookService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.nio.file.Path;
import java.util.*;

@Service
public class RecipeBookServiceImpl implements RecipeBookService {
    @Value("${recipe.file}")
    private String recipeFile;
    private Path path;
    private static Long id = 0L;
    private static Map<Long, Recipe> recipes = new HashMap<>();
    private final IngredientsService ingredientsService;
    private final FileService fileService;

    @PostConstruct
    private void init() {
        path = Path.of(recipeFile);
        readFromFile();
    }

    public RecipeBookServiceImpl(IngredientsService ingredientsService, FileService fileService) {
        this.ingredientsService = ingredientsService;
        this.fileService = fileService;
    }


    @Override
    public void addRecipe(Recipe recipe) {
        if (!recipes.containsValue(recipe)) {
            recipes.put(id++, recipe);
            saveToFile();
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

        addRecipe(new Recipe(name, cookingTime, ingredients, stepList));
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
            saveToFile();
            return true;
        }
        return false;
    }

    @Override
    public boolean editRecipe(Long i, Recipe recipe) {
        if (recipes.containsKey(i)) {
            recipes.replace(i, recipe);
            saveToFile();
            return true;
        }
        return false;
    }

    private void saveToFile() {
        try {
            String json = new ObjectMapper().writeValueAsString(recipes);
            fileService.saveToFile(path, json);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private void readFromFile() {
        try {
            String json = fileService.readFromFile(path);
            recipes = new ObjectMapper().readValue(json, new TypeReference<Map<Long, Recipe>>() {
            });
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
