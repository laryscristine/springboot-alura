package com.example.alura.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.net.URI;
import java.net.URISyntaxException;

@ActiveProfiles("test")
@SpringBootTest
@RunWith(SpringRunner.class)
class AuthControllerTest {

    @Test
    public void retorna400SeDadosdeAutenticaoIncorretos() throws URISyntaxException {
        URI uri = new URI("/auth");
    }
}