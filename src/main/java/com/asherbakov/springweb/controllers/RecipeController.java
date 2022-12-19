package com.asherbakov.springweb.controllers;

import com.asherbakov.springweb.models.Ingredient;
import com.asherbakov.springweb.models.Recipe;
import com.asherbakov.springweb.services.impl.IngredientsImpl;
import com.asherbakov.springweb.services.impl.RecipeBookImpl;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
@RequestMapping("/recipe")
public class RecipeController {
    private String name;
    private int cookingTime;
    private Map<Integer, Ingredient> ingredients = new HashMap<>();
    private List<String> steps;

    @GetMapping("/add")
    private String addRecipe(@RequestParam String name,
                             @RequestParam("time") int cookingTime,
                             @RequestParam Integer[] ingId,
                             @RequestParam String[] steps) {
        Map<Integer, Ingredient> ingredients = new HashMap<>();
        String answer = "";

        if (ingId.length > 0) {
            int id = 0;
            for (Integer i : ingId) {
                ingredients.put(id++, new IngredientsImpl().getIngredient(i));
            }
        }

        if (steps.length > 0) {
            this.steps = new ArrayList<>(Arrays.stream(steps).toList());
        } else {
            this.steps = new ArrayList<>();
        }
        try {
            new RecipeBookImpl().addRecipe(new Recipe(name, cookingTime, ingredients, this.steps));
            answer = "Рецепт добавлен.";
        } catch (Exception e) {
            answer = "Ошибка: " + e.getMessage();
        }
        return answer;
    }

    @GetMapping("/all")
    private Map<Integer, Recipe> getAllRecipes() {
        return new RecipeBookImpl().getAllRecipes();
    }

    @GetMapping
    private Recipe getRecipe(@RequestParam Integer id) {
        return new RecipeBookImpl().getRecipe(id);
    }
}
