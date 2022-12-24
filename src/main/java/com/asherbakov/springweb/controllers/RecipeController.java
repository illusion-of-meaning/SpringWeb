package com.asherbakov.springweb.controllers;

import com.asherbakov.springweb.models.Recipe;
import com.asherbakov.springweb.services.RecipeBookService;
import com.asherbakov.springweb.services.impl.RecipeBookImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/recipe")
public class RecipeController {
    @Autowired
    RecipeBookService recipeBookService;

    @GetMapping("/add")
    private ResponseEntity<String> addRecipe(@RequestParam String name,
                                             @RequestParam("time") int cookingTime,
                                             @RequestParam Long[] ingId,
                                             @RequestParam String[] steps) {
        try {
            recipeBookService.addRecipe(name, cookingTime, ingId, steps);
        } catch (Exception e) {
            return ResponseEntity.unprocessableEntity().build();
        }
        return ResponseEntity.ok("Рецепт добавлен.<br><a href=\"http://127.0.0.1:8080/\" style=\"display:block;margin-top:10px;\">На главную</a>");
    }

    @GetMapping("/all")
    private ResponseEntity<Map<Long, Recipe>> getAllRecipes() {
        return ResponseEntity.ok(recipeBookService.getAllRecipes());
    }

    @GetMapping
    private ResponseEntity<Recipe> getRecipe(@RequestParam Long id) {
        Recipe recipe = new RecipeBookImpl().getRecipe(id);
        if (recipe == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(recipe);
    }

    @DeleteMapping("/{id}")
    private ResponseEntity<String> deleteRecipe(@PathVariable long id) {
        if (!recipeBookService.removeRecipe(id)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok("Рецепт удалён.");
    }

    @PutMapping("/{id}")
    private ResponseEntity<String> editRecipe(@PathVariable Long id,
                                              @RequestBody Recipe recipe) {
        if (!recipeBookService.editRecipe(id, recipe)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok("Рецепт изменён.");
    }
}
