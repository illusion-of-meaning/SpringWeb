package com.asherbakov.springweb.controllers;

import com.asherbakov.springweb.services.FileService;
import com.asherbakov.springweb.services.RecipeBookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

@RestController
@Tag(name = "Работа с файлами")
@RequestMapping("/files")
public class FilesController {
    @Value("${recipe.file}")
    private String recipeFile;
    @Value("${ingredient.file}")
    private String ingredientFile;

    private final FileService fileService;
    private final RecipeBookService recipeBookService;

    public FilesController(FileService fileService, RecipeBookService recipeBookService) {
        this.fileService = fileService;
        this.recipeBookService = recipeBookService;
    }

    @Operation(summary = "Экспорт",
            description = "Экспрот рецептов в файл")
    @GetMapping("/export")
    private ResponseEntity<InputStreamResource> exportFiles() throws FileNotFoundException {
        File file = fileService.getDataFile(recipeFile);
        if (file.exists()) {
            InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"recipes.json\"")
                    .contentLength(file.length())
                    .body(resource);
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @Operation(summary = "Скачать рецепты",
            description = "Скачивание рецептов в текстовый файл")
    @GetMapping("/download")
    private ResponseEntity<byte[]> downloadFiles() {
        String recipes = recipeBookService.getAllTextRecipes();
        if (recipes != null && !recipes.isBlank()) {
            byte[] result = recipes.getBytes();
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"recipes.txt\"")
                    .body(result);
        }
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Импорт рецептов",
            description = "Импорт рецептов из файла")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Рецепты импортированы")
    })
    @PostMapping(value = "/import/recipe", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> uploadRecipeFile(@RequestParam MultipartFile fromFile) {
        File toFile = fileService.getDataFile(recipeFile);
        return uploadFile(fromFile, toFile);
    }

    @Operation(summary = "Импорт ингредиентов",
            description = "Импорт ингредиентов из файла")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Ингредиенты импортированы")
    })
    @PostMapping(value = "/import/ingredient", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> uploadIngredientFile(@RequestParam MultipartFile fromFile) {
        File toFile = fileService.getDataFile(ingredientFile);
        return uploadFile(fromFile, toFile);
    }

    /***
     *
     * @param fromFile загружаемый файл
     * @param toFile заменяемый файл
     */
    private ResponseEntity<Void> uploadFile(MultipartFile fromFile, File toFile) {
        try (FileOutputStream fos = new FileOutputStream(toFile)) {
            IOUtils.copy(fromFile.getInputStream(), fos);
            return ResponseEntity.ok().build();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
}
