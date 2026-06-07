package br.com.fiap.orbitAlert.control;

import br.com.fiap.orbitAlert.config.RecursoNaoEncontradoException;
import br.com.fiap.orbitAlert.dto.EstacaoIotDTO;
import br.com.fiap.orbitAlert.mapper.IEstacaoIotMapper;
import br.com.fiap.orbitAlert.model.EstacaoIot;
import br.com.fiap.orbitAlert.repository.IEstacaoIotRepository;
import br.com.fiap.orbitAlert.service.EstacaoIotCachingService;
import br.com.fiap.orbitAlert.service.EstacaoIotPaginacaoService;
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
@RequestMapping(value = "/estacao-iot")
public class EstacaoIotController {

    @Autowired private IEstacaoIotRepository repository;
    @Autowired private IEstacaoIotMapper mapper;
    @Autowired private EstacaoIotCachingService cachingService;
    @Autowired private EstacaoIotPaginacaoService paginacaoService;

    @Operation(description = "Retorna todos os registros de EstacaoIot", summary = "Retorna EstacaoIotDTO", tags = "Retorno de Informações")
    @GetMapping("/todos")
    public ResponseEntity<List<EstacaoIotDTO>> retornarTodos() {
        return ResponseEntity.ok(cachingService.findAll().stream()
                .map(mapper::toDTO).collect(Collectors.toList()));
    }

    @Operation(description = "Retorna EstacaoIot paginado", summary = "Retorna EstacaoIotDTO paginado", tags = "Retorno de Informações")
    @GetMapping("/paginar")
    public ResponseEntity<Page<EstacaoIotDTO>> paginar(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "5") Integer size) {
        PageRequest request = PageRequest.of(page, size);
        return ResponseEntity.ok(paginacaoService.paginar(request).map(mapper::toDTO));
    }

    @Operation(description = "Retorna EstacaoIot por ID com HATEOAS", summary = "Retorna EstacaoIot por ID", tags = "Retorno de Informações")
    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<EstacaoIotDTO>> retornarPorId(@PathVariable Long id) {
        EstacaoIot entidade = cachingService.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("EstacaoIot", id));
        EstacaoIotDTO dto = mapper.toDTO(entidade);
        Link selfLink = linkTo(methodOn(EstacaoIotController.class).retornarPorId(id)).withSelfRel();
        Link todosLink = linkTo(methodOn(EstacaoIotController.class).retornarTodos()).withRel("todos");
        return ResponseEntity.ok(EntityModel.of(dto, selfLink, todosLink));
    }

    @Operation(description = "Inserir novo EstacaoIot", summary = "Inserir EstacaoIot", tags = "Inserção de Informações")
    @PostMapping("/novo")
    public ResponseEntity<EstacaoIotDTO> inserir(@RequestBody @Valid EstacaoIotDTO dto) {
        EstacaoIot salvo = repository.save(mapper.toEntity(dto));
        cachingService.removerCache();
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toDTO(salvo));
    }

    @Operation(description = "Remover EstacaoIot", summary = "Remover EstacaoIot", tags = "Remoção de Informações")
    @DeleteMapping("/remover/{id}")
    public ResponseEntity<Void> remover(@PathVariable Long id) {
        EstacaoIot entidade = repository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("EstacaoIot", id));
        repository.delete(entidade);
        cachingService.removerCache();
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Operation(description = "Atualizar EstacaoIot", summary = "Atualizar EstacaoIot", tags = "Atualização de Informações")
    @PutMapping("/atualizar/{id}")
    public ResponseEntity<EstacaoIotDTO> atualizar(@PathVariable Long id, @RequestBody @Valid EstacaoIotDTO dto) {
        EstacaoIot antigo = cachingService.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("EstacaoIot", id));
        antigo.atualizar(mapper.toEntity(dto));
        repository.save(antigo);
        cachingService.removerCache();
        return ResponseEntity.ok(mapper.toDTO(antigo));
    }
}