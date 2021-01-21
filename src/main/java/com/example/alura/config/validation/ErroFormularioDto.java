package com.example.alura.config.validation;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ErroFormularioDto {

    private String campo;
    private String erro;

}
