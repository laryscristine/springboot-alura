package com.example.alura.form;

import com.example.alura.modelo.Curso;
import com.example.alura.modelo.Topico;
import com.example.alura.repository.CursoRepository;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class TopicoForm {

    @NotNull
    @NotEmpty
    @Length(min = 5)
    private String titulo;

    @NotNull
    @NotEmpty
    @Length(min = 10)
    private String mensagem;


    @NotNull
    @NotEmpty
    @Length(min = 3)
    private String nomeCurso;

    public Topico converter(CursoRepository cursoRepository) {

        Curso curso = cursoRepository.findByNome(nomeCurso);
        return new Topico(titulo, mensagem, curso);

    }
}
