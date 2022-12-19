package com.asherbakov.springweb.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@RestController
public class MainController {
    @GetMapping("/")
    private String mainPage() {
        return """
                <h1>Примеры команд</h1>
                <h3>Добавить ингредиеты:</h3>
                http://127.0.0.1:8080/ingredient/add?name=Картофель&weight=300&measure=гр<br>
                http://127.0.0.1:8080/ingredient/add?name=Помидоры&weight=2&measure=шт.
                <h3>Вывести список всех ингредиентов:</h3>
                http://127.0.0.1:8080/ingredient/all
                <h3>Вывести информацию по ингредиенту:</h3>
                http://127.0.0.1:8080/ingredient/?id=1
                <h3>Добавить рецепт:</h3>
                http://127.0.0.1:8080/recipe/add?name=Картофель с помидорами&time=30&ingId=0,1&steps=почистить картофель,нарезать картофель,нарезать помидоры,обжарить всё<br>
                <h3>Вывести список всех рецептов:</h3>
                http://127.0.0.1:8080/recipe/all
                <h3>Вывести информацию по рецепту:</h3>
                http://127.0.0.1:8080/recipe/?id=0""";
    }

    @GetMapping("/info")
    private String infoPage() {
        final String AUTHOR = "Щербаков Антон";
        final String NAME = "первое Web-приложение";
        final LocalDate CREATE_DATE = LocalDate.parse("12.12.2022", DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        final String ABOUT = "web-приложение на Spring";

        return String.format("<strong>Имя ученика:</strong> %s;<br>" +
                "<strong>проект:</strong> %s;<br>" +
                "<strong>дата создания:</strong> %s;<br>" +
                "<strong>описание:</strong> %s", AUTHOR, NAME, CREATE_DATE, ABOUT);
    }
}
