package com.asherbakov.springweb.services;

import com.asherbakov.springweb.models.Ingredient;

import java.util.Map;

public interface IngredientsService {
    void addIngredient(Ingredient ingredient);
    Map<Integer, Ingredient> getAllIngredients();
    Ingredient getIngredient(Integer i);
}
