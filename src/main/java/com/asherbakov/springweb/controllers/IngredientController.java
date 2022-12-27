package com.asherbakov.springweb.controllers;

import com.asherbakov.springweb.models.Ingredient;
import com.asherbakov.springweb.services.IngredientsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@Tag(name = "Ингредиенты", description = "Обработка списка ингредиентов")
@RequestMapping("/ingredient")
public class IngredientController {
    private final IngredientsService ingredientsService;

    public IngredientController(IngredientsService ingredientsService) {
        this.ingredientsService = ingredientsService;
    }

    @Operation(summary = "Добавление ингредиента")
    @Parameters(value = {
            @Parameter(name = "name", description = "Наименование ингредиента", example = "чеснок"),
            @Parameter(name = "weight", description = "Количество используемого ингредиента", example = "3"),
            @Parameter(name = "measureUnit", description = "Единица измерения", example = "шт.")
    })
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Ингредиент успешно добавлен"
            )
    })
    @GetMapping("/add")
    private ResponseEntity<String> addIngredient(@RequestParam String name,
                                                 @RequestParam int weight,
                                                 @RequestParam("measure") String measureUnit) {
        try {
            ingredientsService.addIngredient(new Ingredient(name, weight, measureUnit));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        return ResponseEntity.ok("Ингредиент добавлен.<br><a href=\"http://127.0.0.1:8080/\" style=\"display:block;margin-top:10px;\">На главную</a>");
    }

    @Operation(
            summary = "Получение списка всех ингредиентов"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Получен список всех ингредиентов",
                    content = {
                            @Content(
                                    mediaType = "application/json"
                            )
                    }
            )
    })
    @GetMapping("/all")
    private ResponseEntity<Map<Long, Ingredient>> getAllIngredients() {
        return ResponseEntity.ok(ingredientsService.getAllIngredients());
    }

    @Operation(
            summary = "Получение ингредиента",
            description = "Получение ингредиента по ID"
    )
    @Parameters(value = {
            @Parameter(name = "id", description = "Идентификатор ингредиента", example = "0")
    })
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Ингредиент получен",
                    content = {
                            @Content(
                                    mediaType = "application/json"
                            )
                    }
            )
    })
    @GetMapping
    private ResponseEntity<Ingredient> getIngredient(@RequestParam Long id) {
        return ResponseEntity.ok(ingredientsService.getIngredient(id));
    }

    @Operation(
            summary = "Удаление ингредиента",
            description = "Удаление ингредиента по ID"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Ингредиент удален"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Ингредиент не найден"
            )
    })
    @DeleteMapping("/{id}")
    private ResponseEntity<String> deleteIngredient(@PathVariable Long id) {
        if (!ingredientsService.removeIngredient(id)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok("Ингредиент удалён.");
    }

    @Operation(summary = "Изменение ингредиента")
    @Parameters(value = {
            @Parameter(name = "id", example = "0"),
            @Parameter(name = "ingredient", example = """
                    {
                        "name": "Редис",
                        "weight": 2,
                        "measureUnit": "шт."
                    }
                    """)
    })
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Ингредиент изменен"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Не удалось изменить ингредиент"
            )
    })
    @PutMapping("/{id}")
    private ResponseEntity<String> editIngredient(@PathVariable Long id,
                                                  @RequestBody Ingredient ingredient) {
        if (!ingredientsService.editIngredient(id, ingredient)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok("Ингредиент изменён.");
    }
}
