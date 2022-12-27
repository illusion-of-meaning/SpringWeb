package com.asherbakov.springweb.services;

import com.asherbakov.springweb.models.Ingredient;

import java.util.Map;

public interface IngredientsService {
    void addIngredient(Ingredient ingredient);
    Map<Long, Ingredient> getAllIngredients();
    Ingredient getIngredient(Long i);

    boolean removeIngredient(Long id);

    boolean editIngredient(Long i, Ingredient ingredient);
}
