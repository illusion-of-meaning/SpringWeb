package com.asherbakov.springweb.models;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@EqualsAndHashCode
public class Recipe {
    private String name;
    private int cookingTime;
    private Map<Integer, Ingredient> ingredients = new HashMap<>();
    private List<String> steps = new ArrayList<>();

    public Recipe(String name, int cookingTime, Map<Integer, Ingredient> ingredients, List<String> steps) {
        setName(name);
        setIngredients(ingredients);
        setSteps(steps);
        setCookingTime(cookingTime);
    }

    public void setName(String name) {
        if (name != null && !name.isBlank()) {
            this.name = name;
        } else {
            throw new IllegalArgumentException("Не задано название рецепта");
        }
    }

    public void setCookingTime(int cookingTime) {
        if (cookingTime > 0) {
            this.cookingTime = cookingTime;
        } else {
            throw new IllegalArgumentException("Время приготовления не задано, либо задано не корректно.");
        }
    }

    public void setIngredients(Map<Integer, Ingredient> ingredients) {
        if (!ingredients.isEmpty()) {
            this.ingredients = ingredients;
        } else {
            throw new IllegalArgumentException("Список ингредиентов пуст.");
        }
    }

    public void setSteps(List<String> steps) {
        if (!steps.isEmpty()) {
            this.steps = steps;
        } else {
            throw new IllegalArgumentException("Список шагов приготовления пуст.");
        }
    }

    @Override
    public String toString() {
//        StringBuffer result = new StringBuffer();
//        result.append(name);
//        result.append(cookingTime);

        return String.format("%s:\n\tвремя приготовления: %s\n\tингредиенты: %s\n\tшаги приготовления: %s", name, cookingTime, ingredients, steps);
    }
}
