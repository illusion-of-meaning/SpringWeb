package com.asherbakov.springweb.controllers;

import com.asherbakov.springweb.models.Recipe;
import com.asherbakov.springweb.services.RecipeBookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/recipe")
public class RecipeController {
    private final RecipeBookService recipeBookService;

    public RecipeController(RecipeBookService recipeBookService) {
        this.recipeBookService = recipeBookService;
    }

    @Operation(summary = "Добавление рецепта")
    @Parameters(value = {
            @Parameter(name = "name", description = "Название рецепта", example = "Салат с крабовыми палочками"),
            @Parameter(name = "cookingTime", description = "Время приготовления (минут)", example = "30"),
            @Parameter(name = "ingId", description = "Идентификаторы используемых ингредиентов", example = "0,1"),
            @Parameter(name = "steps", description = "Инструкция по приготовлению", example = "нарезать крабовые палочки, сделать салат"),
    })
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Рецепт добавлен"
            )
    })
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

    @Operation(summary = "Получить список всех рецептов")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    content = {
                            @Content(
                                    mediaType = "Application/json"
                            )
                    }
            )
    })
    @GetMapping("/all")
    private ResponseEntity<Map<Long, Recipe>> getAllRecipes() {
        return ResponseEntity.ok(recipeBookService.getAllRecipes());
    }

    @Operation(summary = "Отобразить рецепт")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    content = {
                            @Content(
                                    mediaType = "Application/json"
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Рецепт не найден"
            )
    })
    @GetMapping
    private ResponseEntity<Recipe> getRecipe(@RequestParam Long id) {
        Recipe recipe = recipeBookService.getRecipe(id);
        if (recipe == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(recipe);
    }

    @Operation(summary = "Удалить рецепт", description = "Удалить рецепт по ID")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Рецепт удален"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Рецепт не найден"
            )
    })
    @DeleteMapping("/{id}")
    private ResponseEntity<String> deleteRecipe(@PathVariable long id) {
        if (!recipeBookService.removeRecipe(id)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok("Рецепт удалён.");
    }

    @Operation(summary = "Изменить рецепт", description = "Изменить рецепт по ID")
    @Parameters(value = {
            @Parameter(name = "id", description = "Id изменяемого рецепта", example = "0"),
            @Parameter(name = "recipe", description = "Новый рецепт", example = """
                    {
                        "name": "Картофель без помидор",
                        "cookingTime": 30,
                        "ingredients": {
                            "0": {
                                "name": "Картофель",
                                "weight": 300,
                                "measureUnit": "гр"
                            }
                        },
                        "steps": [
                            "почистить картофель",
                            "нарезать картофель",
                            "обжарить"
                        ]
                    }
                    """)
    })
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Рецепт изменен"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Рецепт не найден"
            )
    })
    @PutMapping("/{id}")
    private ResponseEntity<String> editRecipe(@PathVariable Long id,
                                              @RequestBody Recipe recipe) {
        if (!recipeBookService.editRecipe(id, recipe)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok("Рецепт изменён.");
    }
}
