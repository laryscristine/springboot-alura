package com.example.alura.repository;

import com.example.alura.modelo.Curso;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@ActiveProfiles("test")
@DataJpaTest ///spring ja traz testes para repository (especifico)
@RunWith(SpringRunner.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CursoRepositoryTest {

    @Autowired
    private CursoRepository cursoRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void seEncontradoRetornaNomedoCurso() {
        String nomeCurso = "HTML 5";
        Curso html = new Curso();
        html.setNome("HTML 5");
        html.setCategoria("Programação");
        entityManager.persist(html);
        Curso curso = cursoRepository.findByNome(nomeCurso);
        Assert.assertNotNull(curso);
        Assert.assertEquals(nomeCurso, curso.getNome());
    }

    @Test
    public void seNaoEncontradoNaoRetornaNomedoCurso() {
        String nomeCurso = "HTML";
        Curso curso = cursoRepository.findByNome(nomeCurso);
        Assert.assertNull(curso);
    }
}