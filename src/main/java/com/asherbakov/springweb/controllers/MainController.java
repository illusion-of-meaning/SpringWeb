package com.asherbakov.springweb.controllers;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@RestController
public class MainController {
    @GetMapping("/")
    private String mainPage() {
        String msg = "Приложение запущено";
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
