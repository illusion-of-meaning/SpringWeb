package com.asherbakov.springweb.services.impl;

import com.asherbakov.springweb.models.Ingredient;
import com.asherbakov.springweb.services.IngredientsService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class IngredientsImpl implements IngredientsService {
    private static Integer id = 0;
    private static final Map<Integer, Ingredient> ingredients = new HashMap<>();
    @Override
    public void addIngredient(Ingredient ingredient) {
        if (!ingredients.containsValue(ingredient)) {
            ingredients.put(id++, ingredient);
        }
    }

    @Override
    public Map<Integer, Ingredient> getAllIngredients() {
        return ingredients;
    }

    @Override
    public Ingredient getIngredient(Integer i) {
        return ingredients.getOrDefault(i, null);
    }
}
