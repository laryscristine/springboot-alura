package com.example.alura.config.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class ErroValidacaoHandler {

    @Autowired
    private MessageSource messageSource;
    //serve para traduzir as mensagens pro idioma do usuario

    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    /// senão definir aqui, por padrão o Spring vai retornar erro 200 "ok"
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public List<ErroFormularioDto> handle(MethodArgumentNotValidException exception) {

        List<ErroFormularioDto> dto = new ArrayList<>();
        List<FieldError> fieldError = exception.getBindingResult().getFieldErrors();
        fieldError.forEach(e -> {
            String mensagem = messageSource.getMessage(e, LocaleContextHolder.getLocale());
            ErroFormularioDto erro = new ErroFormularioDto(e.getField(), mensagem);
            dto.add(erro);

        });
        return dto;

    }
}
