package com.asherbakov.springweb.services.impl;

import com.asherbakov.springweb.models.Ingredient;
import com.asherbakov.springweb.services.IngredientsService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class IngredientsImpl implements IngredientsService {
    private static Long id = 0L;
    private static final Map<Long, Ingredient> ingredients = new HashMap<>();

    @Override
    public void addIngredient(Ingredient ingredient) {
        if (!ingredients.containsValue(ingredient)) {
            ingredients.put(id++, ingredient);
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
            return true;
        }
        return false;
    }

    @Override
    public boolean editIngredient(Long i, Ingredient ingredient) {
        if (ingredients.containsKey(i)) {
            ingredients.replace(i, ingredient);
            return true;
        }
        return false;
    }
}
