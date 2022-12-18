package com.asherbakov.springweb.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@RestController
public class MainController {
    @GetMapping("/")
    private String mainPage() {
        String msg = "<h1>Примеры команд</h1>\n" +
                "<h3>Добавить ингредиеты:</h3>\n" +
                "http://127.0.0.1:8080/ingredient/add?name=\"Картофель\"&weight=300&measure=\"гр\"<br>\n" +
                "http://127.0.0.1:8080/ingredient/add?name=\"Помидоры\"&weight=2&measure=\"шт.\"\n" +
                "<h3>Вывести список всех ингредиентов:</h3>\n" +
                "http://127.0.0.1:8080/ingredient/get-all\n" +
                "<h3>Вывести информацию по ингредиенту:</h3>\n" +
                "http://127.0.0.1:8080/ingredient/get?id=1\n" +
                "<h3>Добавить рецепт:</h3>\n" +
                "http://127.0.0.1:8080/recipe/add?name=\"Картофель с помидорами\"&time=30&ingId=1,2&steps=\"почистить картофель\",\"нарезать картофель\",\"нарезать помидоры\",\"обжарить всё\"<br>\n" +
                "<h3>Вывести список всех рецептов:</h3>\n" +
                "http://127.0.0.1:8080/recipe/get-all\n" +
                "<h3>Вывести информацию по рецепту:</h3>\n" +
                "http://127.0.0.1:8080/recipe/get?id=1";
        return msg;
    }

    @GetMapping("/info")
    private String infoPage() {
        final String AUTHOR = "Щербаков Антон";
        final String NAME = "первое Web-приложение";
        final LocalDate CREATE_DATE = LocalDate.parse("12.12.2022", DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        final String ABOUT = "web-приложение на Spring";

        String msg = String.format("<strong>Имя ученика:</strong> %s;<br>" +
                "<strong>проект:</strong> %s;<br>" +
                "<strong>дата создания:</strong> %s;<br>" +
                "<strong>описание:</strong> %s", AUTHOR, NAME, CREATE_DATE, ABOUT);
        return msg;
    }
}
