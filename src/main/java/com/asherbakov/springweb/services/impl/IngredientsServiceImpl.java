package com.asherbakov.springweb.services.impl;

import com.asherbakov.springweb.models.Ingredient;
import com.asherbakov.springweb.services.FileService;
import com.asherbakov.springweb.services.IngredientsService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

@Service
public class IngredientsServiceImpl implements IngredientsService {
    @Value("${ingredient.file}")
    private String ingredientFile;
    private Path path;
    private static Long id = 0L;
    private static Map<Long, Ingredient> ingredients = new HashMap<>();
    private final FileService fileService;

    @PostConstruct
    private void init() {
        path = Path.of(ingredientFile);
        readFromFile();
    }

    public IngredientsServiceImpl(FileService fileService) {
        this.fileService = fileService;
    }

    @Override
    public void addIngredient(Ingredient ingredient) {
        if (!ingredients.containsValue(ingredient)) {
            ingredients.put(id++, ingredient);
            saveToFile();
        }
    }

    @Override
    public Map<Long, Ingredient> getAllIngredients() {
        return ingredients;
    }

    @Override
    public Ingredient getIngredient(Long i) {
        return ingredients.getOrDefault(i, null);
    }

    @Override
    public boolean removeIngredient(Long i) {
        if (ingredients.containsKey(i)) {
            ingredients.remove(i);
            saveToFile();
            return true;
        }
        return false;
    }

    @Override
    public boolean editIngredient(Long i, Ingredient ingredient) {
        if (ingredients.containsKey(i)) {
            ingredients.replace(i, ingredient);
            saveToFile();
            return true;
        }
        return false;
    }

    private void saveToFile() {
        try {
            String json = new ObjectMapper().writeValueAsString(ingredients);
            fileService.saveToFile(path, json);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private void readFromFile() {
        try {
            String json = fileService.readFromFile(path);
            ingredients = new ObjectMapper().readValue(json, new TypeReference<Map<Long, Ingredient>>() {
            });
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
