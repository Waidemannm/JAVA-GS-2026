package br.com.fiap.orbitAlert.control;

import br.com.fiap.orbitAlert.config.RecursoNaoEncontradoException;
import br.com.fiap.orbitAlert.dto.ZonaRiscoDTO;
import br.com.fiap.orbitAlert.mapper.IZonaRiscoMapper;
import br.com.fiap.orbitAlert.model.ZonaRisco;
import br.com.fiap.orbitAlert.repository.IZonaRiscoRepository;
import br.com.fiap.orbitAlert.service.ZonaRiscoCachingService;
import br.com.fiap.orbitAlert.service.ZonaRiscoPaginacaoService;
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
@RequestMapping(value = "/zona-risco")
public class ZonaRiscoController {

    @Autowired private IZonaRiscoRepository repository;
    @Autowired private IZonaRiscoMapper mapper;
    @Autowired private ZonaRiscoCachingService cachingService;
    @Autowired private ZonaRiscoPaginacaoService paginacaoService;

    @Operation(description = "Retorna todos os registros de ZonaRisco", summary = "Retorna ZonaRiscoDTO", tags = "Retorno de Informações")
    @GetMapping("/todos")
    public ResponseEntity<List<ZonaRiscoDTO>> retornarTodos() {
        return ResponseEntity.ok(cachingService.findAll().stream()
                .map(mapper::toDTO).collect(Collectors.toList()));
    }

    @Operation(description = "Retorna ZonaRisco paginado", summary = "Retorna ZonaRiscoDTO paginado", tags = "Retorno de Informações")
    @GetMapping("/paginar")
    public ResponseEntity<Page<ZonaRiscoDTO>> paginar(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "5") Integer size) {
        PageRequest request = PageRequest.of(page, size);
        return ResponseEntity.ok(paginacaoService.paginar(request).map(mapper::toDTO));
    }

    @Operation(description = "Retorna ZonaRisco por ID com HATEOAS", summary = "Retorna ZonaRisco por ID", tags = "Retorno de Informações")
    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<ZonaRiscoDTO>> retornarPorId(@PathVariable Long id) {
        ZonaRisco entidade = cachingService.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("ZonaRisco", id));
        ZonaRiscoDTO dto = mapper.toDTO(entidade);
        Link selfLink = linkTo(methodOn(ZonaRiscoController.class).retornarPorId(id)).withSelfRel();
        Link todosLink = linkTo(methodOn(ZonaRiscoController.class).retornarTodos()).withRel("todos");
        return ResponseEntity.ok(EntityModel.of(dto, selfLink, todosLink));
    }

    @Operation(description = "Inserir novo ZonaRisco", summary = "Inserir ZonaRisco", tags = "Inserção de Informações")
    @PostMapping("/novo")
    public ResponseEntity<ZonaRiscoDTO> inserir(@RequestBody @Valid ZonaRiscoDTO dto) {
        ZonaRisco salvo = repository.save(mapper.toEntity(dto));
        cachingService.removerCache();
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toDTO(salvo));
    }

    @Operation(description = "Remover ZonaRisco", summary = "Remover ZonaRisco", tags = "Remoção de Informações")
    @DeleteMapping("/remover/{id}")
    public ResponseEntity<Void> remover(@PathVariable Long id) {
        ZonaRisco entidade = repository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("ZonaRisco", id));
        repository.delete(entidade);
        cachingService.removerCache();
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Operation(description = "Atualizar ZonaRisco", summary = "Atualizar ZonaRisco", tags = "Atualização de Informações")
    @PutMapping("/atualizar/{id}")
    public ResponseEntity<ZonaRiscoDTO> atualizar(@PathVariable Long id, @RequestBody @Valid ZonaRiscoDTO dto) {
        ZonaRisco antigo = cachingService.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("ZonaRisco", id));
        antigo.atualizar(mapper.toEntity(dto));
        repository.save(antigo);
        cachingService.removerCache();
        return ResponseEntity.ok(mapper.toDTO(antigo));
    }
}