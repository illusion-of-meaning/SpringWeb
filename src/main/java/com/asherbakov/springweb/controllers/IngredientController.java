package com.asherbakov.springweb.controllers;

import com.asherbakov.springweb.models.Ingredient;
import com.asherbakov.springweb.services.IngredientsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/ingredient")
public class IngredientController {
    private final IngredientsService ingredientsService;

    public IngredientController(IngredientsService ingredientsService) {
        this.ingredientsService = ingredientsService;
    }

    @GetMapping("/add")
    private ResponseEntity<String> addIngredient(@RequestParam String name,
                                                 @RequestParam int weight,
                                                 @RequestParam("measure") String measureUnit) {
        try {
            ingredientsService.addIngredient(new Ingredient(name, weight, measureUnit));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        return ResponseEntity.ok("Игредиент добавлен.<br><a href=\"http://127.0.0.1:8080/\" style=\"display:block;margin-top:10px;\">На главную</a>");
    }

    @GetMapping("/all")
    private ResponseEntity<Map<Long, Ingredient>> getAllIngredients() {
        return ResponseEntity.ok(ingredientsService.getAllIngredients());
    }

    @GetMapping
    private ResponseEntity<Ingredient> getIngredient(@RequestParam Long id) {
        return ResponseEntity.ok(ingredientsService.getIngredient(id));
    }

    @DeleteMapping("/{id}")
    private ResponseEntity<String> deleteIngredient(@PathVariable Long id) {
        if (!ingredientsService.removeIngredient(id)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok("Ингредиент удалён.");
    }

    @PutMapping("/{id}")
    private ResponseEntity<String> editIngredient(@PathVariable Long id,
                                                  @RequestBody Ingredient ingredient) {
        if (!ingredientsService.editIngredient(id, ingredient)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok("Ингредиент изменён.");
    }
}
