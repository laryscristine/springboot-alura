package com.example.alura.controller;

import com.example.alura.controller.dto.DetalhesTopicoDTO;
import com.example.alura.controller.dto.TopicoDTO;
import com.example.alura.form.AtualizacaoTopicoForm;
import com.example.alura.form.TopicoForm;
import com.example.alura.modelo.Topico;
import com.example.alura.repository.CursoRepository;
import com.example.alura.repository.TopicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.net.URI;
import java.util.Optional;

@RequestMapping("/topicos")
@RestController
public class TopicController {

    @Autowired
    private TopicoRepository topicoRepository;

    @Autowired
    private CursoRepository cursoRepository;

    @Cacheable(value = "listaDeTopicos")
    @GetMapping
    public Page<TopicoDTO> lista(@RequestParam(required = false) String nomeCurso, @PageableDefault(sort = "id", direction = Sort.Direction.DESC, page = 0, size = 10) Pageable paginacao) {

        if (nomeCurso == null) {
            Page<Topico> topicos = topicoRepository.findAll(paginacao);
            return TopicoDTO.converter(topicos);
        } else {
            Page<Topico> topicos = topicoRepository.findByCursoNome(nomeCurso, paginacao);
            return TopicoDTO.converter(topicos);
        }

    }

    @Transactional
    @PostMapping
    @CacheEvict(value = "listaDeTopicos", allEntries = true)
    public ResponseEntity<TopicoDTO> cadastrar(@Valid @RequestBody TopicoForm form, UriComponentsBuilder uriBuilder) {

        Topico topico = form.converter(cursoRepository);
        topicoRepository.save(topico);
        URI uri = uriBuilder.path("/topicos/{id}").buildAndExpand(topico.getId()).toUri(); //Constroe uma "url" automaticamente
        return ResponseEntity.created(uri).body(new TopicoDTO(topico)); //passa no corpo o topico criado

    }

    @CacheEvict(value = "listaDeTopicos", allEntries = true)
    @Transactional //commita as alterações ao final do metodo
    @PutMapping("/{id}")
    public ResponseEntity<TopicoDTO> atualizar(@PathVariable Long id, @Valid @RequestBody AtualizacaoTopicoForm atualizacaoTopicoForm) {

        Optional<Topico> optional = topicoRepository.findById(id);

        if (optional.isPresent()) {
            Topico topico = atualizacaoTopicoForm.atualizar(id, topicoRepository);
            return ResponseEntity.ok(new TopicoDTO(topico));
        }
        return ResponseEntity.notFound().build();

    }

    @CacheEvict(value = "listaDeTopicos", allEntries = true)
    @Transactional
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletar(@PathVariable Long id) {

        Optional<Topico> optional = topicoRepository.findById(id);

        if (optional.isPresent()) {
            topicoRepository.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<DetalhesTopicoDTO> detalhar(@PathVariable Long id) {

        Optional<Topico> optional = topicoRepository.findById(id);
        if (optional.isPresent()) {
            return ResponseEntity.ok(new DetalhesTopicoDTO(optional.get()));
        }

        return ResponseEntity.notFound().build();

    }

}
