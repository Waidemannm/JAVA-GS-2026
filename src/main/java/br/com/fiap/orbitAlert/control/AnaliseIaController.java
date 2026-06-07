package br.com.fiap.orbitAlert.control;

import br.com.fiap.orbitAlert.config.RecursoNaoEncontradoException;
import br.com.fiap.orbitAlert.dto.AnaliseIaDTO;
import br.com.fiap.orbitAlert.mapper.IAnaliseIaMapper;
import br.com.fiap.orbitAlert.model.AnaliseIa;
import br.com.fiap.orbitAlert.repository.IAnaliseIaRepository;
import br.com.fiap.orbitAlert.service.AnaliseIaCachingService;
import br.com.fiap.orbitAlert.service.AnaliseIaPaginacaoService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping(value = "/analise-ia")
public class AnaliseIaController {

    @Autowired private IAnaliseIaRepository repository;
    @Autowired private IAnaliseIaMapper mapper;
    @Autowired private AnaliseIaCachingService cachingService;
    @Autowired private AnaliseIaPaginacaoService paginacaoService;

    @Operation(description = "Retorna todos os registros de AnaliseIa", summary = "Retorna AnaliseIaDTO", tags = "Retorno de Informações")
    @GetMapping("/todos")
    public ResponseEntity<List<AnaliseIaDTO>> retornarTodos() {
        return ResponseEntity.ok(cachingService.findAll().stream()
                .map(mapper::toDTO).collect(Collectors.toList()));
    }

    @Operation(description = "Retorna AnaliseIa paginado", summary = "Retorna AnaliseIaDTO paginado", tags = "Retorno de Informações")
    @GetMapping("/paginar")
    public ResponseEntity<Page<AnaliseIaDTO>> paginar(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "5") Integer size) {
        PageRequest request = PageRequest.of(page, size);
        return ResponseEntity.ok(paginacaoService.paginar(request).map(mapper::toDTO));
    }

    @Operation(description = "Retorna AnaliseIa por ID com HATEOAS", summary = "Retorna AnaliseIa por ID", tags = "Retorno de Informações")
    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<AnaliseIaDTO>> retornarPorId(@PathVariable Long id) {
        AnaliseIa entidade = cachingService.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("AnaliseIa", id));
        AnaliseIaDTO dto = mapper.toDTO(entidade);
        Link selfLink = linkTo(methodOn(AnaliseIaController.class).retornarPorId(id)).withSelfRel();
        Link todosLink = linkTo(methodOn(AnaliseIaController.class).retornarTodos()).withRel("todos");
        return ResponseEntity.ok(EntityModel.of(dto, selfLink, todosLink));
    }

    @Operation(description = "Inserir novo AnaliseIa", summary = "Inserir AnaliseIa", tags = "Inserção de Informações")
    @PostMapping("/novo")
    public ResponseEntity<AnaliseIaDTO> inserir(@RequestBody @Valid AnaliseIaDTO dto) {
        AnaliseIa salvo = repository.save(mapper.toEntity(dto));
        cachingService.removerCache();
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toDTO(salvo));
    }

    @Operation(description = "Remover AnaliseIa", summary = "Remover AnaliseIa", tags = "Remoção de Informações")
    @DeleteMapping("/remover/{id}")
    public ResponseEntity<Void> remover(@PathVariable Long id) {
        AnaliseIa entidade = repository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("AnaliseIa", id));
        repository.delete(entidade);
        cachingService.removerCache();
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Operation(description = "Atualizar AnaliseIa", summary = "Atualizar AnaliseIa", tags = "Atualização de Informações")
    @PutMapping("/atualizar/{id}")
    public ResponseEntity<AnaliseIaDTO> atualizar(@PathVariable Long id, @RequestBody @Valid AnaliseIaDTO dto) {
        AnaliseIa antigo = cachingService.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("AnaliseIa", id));
        antigo.atualizar(mapper.toEntity(dto));
        repository.save(antigo);
        cachingService.removerCache();
        return ResponseEntity.ok(mapper.toDTO(antigo));
    }
}