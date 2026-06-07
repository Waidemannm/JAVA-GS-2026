package br.com.fiap.orbitAlert.control;

import br.com.fiap.orbitAlert.config.RecursoNaoEncontradoException;
import br.com.fiap.orbitAlert.dto.HistoricoAlertaDTO;
import br.com.fiap.orbitAlert.mapper.IHistoricoAlertaMapper;
import br.com.fiap.orbitAlert.model.HistoricoAlerta;
import br.com.fiap.orbitAlert.repository.IHistoricoAlertaRepository;
import br.com.fiap.orbitAlert.service.HistoricoPaginacaoService;
import br.com.fiap.orbitAlert.service.HistoricoCachingService;
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
@RequestMapping(value = "/historico-alerta")
public class HistoricoAlertaController {

    @Autowired private IHistoricoAlertaRepository repository;
    @Autowired private IHistoricoAlertaMapper mapper;
    @Autowired private HistoricoCachingService cachingService;
    @Autowired private HistoricoPaginacaoService paginacaoService;

    @Operation(description = "Retorna todos os registros de HistoricoAlerta", summary = "Retorna HistoricoAlertaDTO", tags = "Retorno de Informações")
    @GetMapping("/todos")
    public ResponseEntity<List<HistoricoAlertaDTO>> retornarTodos() {
        return ResponseEntity.ok(cachingService.findAll().stream()
                .map(mapper::toDTO).collect(Collectors.toList()));
    }

    @Operation(description = "Retorna HistoricoAlerta paginado", summary = "Retorna HistoricoAlertaDTO paginado", tags = "Retorno de Informações")
    @GetMapping("/paginar")
    public ResponseEntity<Page<HistoricoAlertaDTO>> paginar(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "5") Integer size) {
        PageRequest request = PageRequest.of(page, size);
        return ResponseEntity.ok(paginacaoService.paginar(request).map(mapper::toDTO));
    }

    @Operation(description = "Retorna HistoricoAlerta por ID com HATEOAS", summary = "Retorna HistoricoAlerta por ID", tags = "Retorno de Informações")
    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<HistoricoAlertaDTO>> retornarPorId(@PathVariable Long id) {
        HistoricoAlerta entidade = cachingService.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("HistoricoAlerta", id));
        HistoricoAlertaDTO dto = mapper.toDTO(entidade);
        Link selfLink = linkTo(methodOn(HistoricoAlertaController.class).retornarPorId(id)).withSelfRel();
        Link todosLink = linkTo(methodOn(HistoricoAlertaController.class).retornarTodos()).withRel("todos");
        return ResponseEntity.ok(EntityModel.of(dto, selfLink, todosLink));
    }

    @Operation(description = "Inserir novo HistoricoAlerta", summary = "Inserir HistoricoAlerta", tags = "Inserção de Informações")
    @PostMapping("/novo")
    public ResponseEntity<HistoricoAlertaDTO> inserir(@RequestBody @Valid HistoricoAlertaDTO dto) {
        HistoricoAlerta salvo = repository.save(mapper.toEntity(dto));
        cachingService.removerCache();
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toDTO(salvo));
    }

    @Operation(description = "Remover HistoricoAlerta", summary = "Remover HistoricoAlerta", tags = "Remoção de Informações")
    @DeleteMapping("/remover/{id}")
    public ResponseEntity<Void> remover(@PathVariable Long id) {
        HistoricoAlerta entidade = repository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("HistoricoAlerta", id));
        repository.delete(entidade);
        cachingService.removerCache();
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Operation(description = "Atualizar HistoricoAlerta", summary = "Atualizar HistoricoAlerta", tags = "Atualização de Informações")
    @PutMapping("/atualizar/{id}")
    public ResponseEntity<HistoricoAlertaDTO> atualizar(@PathVariable Long id, @RequestBody @Valid HistoricoAlertaDTO dto) {
        HistoricoAlerta antigo = cachingService.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("HistoricoAlerta", id));
        antigo.atualizar(mapper.toEntity(dto));
        repository.save(antigo);
        cachingService.removerCache();
        return ResponseEntity.ok(mapper.toDTO(antigo));
    }
}