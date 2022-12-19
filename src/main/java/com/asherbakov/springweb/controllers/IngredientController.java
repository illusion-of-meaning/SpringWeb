package com.asherbakov.springweb.controllers;

import com.asherbakov.springweb.models.Ingredient;
import com.asherbakov.springweb.services.impl.IngredientsImpl;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/ingredient")
public class IngredientController {
    @GetMapping("/add")
    private String addIngredient(@RequestParam String name,
                                 @RequestParam int weight,
                                 @RequestParam("measure") String measureUnit) {
        String answer = "";
        try {
            new IngredientsImpl().addIngredient(new Ingredient(name, weight, measureUnit));
            answer = "Ингредиент добавлен.";
        } catch (Exception e) {
            answer = "Ошибка: " + e.getMessage();
        }
        return answer;
    }

    @GetMapping("/all")
    private Map<Integer, Ingredient> getAllIngredients() {
        return new IngredientsImpl().getAllIngredients();
    }

    @GetMapping
    private Ingredient getIngredient(@RequestParam Integer id) {
        return new IngredientsImpl().getIngredient(id);
    }
}
