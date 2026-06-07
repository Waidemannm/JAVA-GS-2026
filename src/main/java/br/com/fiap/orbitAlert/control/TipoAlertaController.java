package br.com.fiap.orbitAlert.control;

import br.com.fiap.orbitAlert.config.RecursoNaoEncontradoException;
import br.com.fiap.orbitAlert.dto.TipoAlertaDTO;
import br.com.fiap.orbitAlert.mapper.ITipoAlertaMapper;
import br.com.fiap.orbitAlert.model.TipoAlerta;
import br.com.fiap.orbitAlert.repository.ITipoAlertaRepository;
import br.com.fiap.orbitAlert.service.TipoAlertaCachingService;
import br.com.fiap.orbitAlert.service.TipoAlertaPaginacaoService;
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
@RequestMapping(value = "/tipo-alerta")
public class TipoAlertaController {

    @Autowired private ITipoAlertaRepository repository;
    @Autowired private ITipoAlertaMapper mapper;
    @Autowired private TipoAlertaCachingService cachingService;
    @Autowired private TipoAlertaPaginacaoService paginacaoService;

    @Operation(description = "Retorna todos os registros de TipoAlerta", summary = "Retorna TipoAlertaDTO", tags = "Retorno de Informações")
    @GetMapping("/todos")
    public ResponseEntity<List<TipoAlertaDTO>> retornarTodos() {
        return ResponseEntity.ok(cachingService.findAll().stream()
                .map(mapper::toDTO).collect(Collectors.toList()));
    }

    @Operation(description = "Retorna TipoAlerta paginado", summary = "Retorna TipoAlertaDTO paginado", tags = "Retorno de Informações")
    @GetMapping("/paginar")
    public ResponseEntity<Page<TipoAlertaDTO>> paginar(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "5") Integer size) {
        PageRequest request = PageRequest.of(page, size);
        return ResponseEntity.ok(paginacaoService.paginar(request).map(mapper::toDTO));
    }

    @Operation(description = "Retorna TipoAlerta por ID com HATEOAS", summary = "Retorna TipoAlerta por ID", tags = "Retorno de Informações")
    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<TipoAlertaDTO>> retornarPorId(@PathVariable Long id) {
        TipoAlerta entidade = cachingService.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("TipoAlerta", id));
        TipoAlertaDTO dto = mapper.toDTO(entidade);
        Link selfLink = linkTo(methodOn(TipoAlertaController.class).retornarPorId(id)).withSelfRel();
        Link todosLink = linkTo(methodOn(TipoAlertaController.class).retornarTodos()).withRel("todos");
        return ResponseEntity.ok(EntityModel.of(dto, selfLink, todosLink));
    }

    @Operation(description = "Inserir novo TipoAlerta", summary = "Inserir TipoAlerta", tags = "Inserção de Informações")
    @PostMapping("/novo")
    public ResponseEntity<TipoAlertaDTO> inserir(@RequestBody @Valid TipoAlertaDTO dto) {
        TipoAlerta salvo = repository.save(mapper.toEntity(dto));
        cachingService.removerCache();
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toDTO(salvo));
    }

    @Operation(description = "Remover TipoAlerta", summary = "Remover TipoAlerta", tags = "Remoção de Informações")
    @DeleteMapping("/remover/{id}")
    public ResponseEntity<Void> remover(@PathVariable Long id) {
        TipoAlerta entidade = repository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("TipoAlerta", id));
        repository.delete(entidade);
        cachingService.removerCache();
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Operation(description = "Atualizar TipoAlerta", summary = "Atualizar TipoAlerta", tags = "Atualização de Informações")
    @PutMapping("/atualizar/{id}")
    public ResponseEntity<TipoAlertaDTO> atualizar(@PathVariable Long id, @RequestBody @Valid TipoAlertaDTO dto) {
        TipoAlerta antigo = cachingService.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("TipoAlerta", id));
        antigo.atualizar(mapper.toEntity(dto));
        repository.save(antigo);
        cachingService.removerCache();
        return ResponseEntity.ok(mapper.toDTO(antigo));
    }
}