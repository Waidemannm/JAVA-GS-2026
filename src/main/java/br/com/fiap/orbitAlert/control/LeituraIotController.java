package br.com.fiap.orbitAlert.control;

import br.com.fiap.orbitAlert.config.RecursoNaoEncontradoException;
import br.com.fiap.orbitAlert.dto.LeituraIotDTO;
import br.com.fiap.orbitAlert.mapper.ILeituraIotMapper;
import br.com.fiap.orbitAlert.model.EstacaoIot;
import br.com.fiap.orbitAlert.model.LeituraIot;
import br.com.fiap.orbitAlert.records.LeituraIotRecord;
import br.com.fiap.orbitAlert.repository.IEstacaoIotRepository;
import br.com.fiap.orbitAlert.repository.ILeituraIotRepository;
import br.com.fiap.orbitAlert.service.LeituraIotCachingService;
import br.com.fiap.orbitAlert.service.LeituraIotPaginacaoService;
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
@RequestMapping(value = "/leitura-iot")
public class LeituraIotController {

    @Autowired private ILeituraIotRepository repository;
    @Autowired private IEstacaoIotRepository estacaoRepository;
    @Autowired private ILeituraIotMapper mapper;
    @Autowired private LeituraIotCachingService cachingService;
    @Autowired private LeituraIotPaginacaoService paginacaoService;

    @Operation(description = "Retorna todas as leituras IoT", summary = "Retorna LeituraIotDTO", tags = "Retorno de Informações")
    @GetMapping("/todos")
    public ResponseEntity<List<LeituraIotDTO>> retornarTodos() {
        return ResponseEntity.ok(cachingService.findAll().stream()
                .map(mapper::toDTO).collect(Collectors.toList()));
    }

    @Operation(description = "Retorna leituras IoT paginadas", summary = "Retorna LeituraIotDTO paginado", tags = "Retorno de Informações")
    @GetMapping("/paginar")
    public ResponseEntity<Page<LeituraIotDTO>> paginar(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "5") Integer size) {
        PageRequest request = PageRequest.of(page, size);
        return ResponseEntity.ok(paginacaoService.paginar(request).map(mapper::toDTO));
    }

    @Operation(description = "Retorna leitura IoT por ID com HATEOAS", summary = "Retorna LeituraIot por ID", tags = "Retorno de Informações")
    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<LeituraIotDTO>> retornarPorId(@PathVariable Long id) {
        LeituraIot entidade = cachingService.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("LeituraIot", id));
        LeituraIotDTO dto = mapper.toDTO(entidade);
        Link selfLink = linkTo(methodOn(LeituraIotController.class).retornarPorId(id)).withSelfRel();
        Link todosLink = linkTo(methodOn(LeituraIotController.class).retornarTodos()).withRel("todos");
        return ResponseEntity.ok(EntityModel.of(dto, selfLink, todosLink));
    }

    @Operation(description = "Recebe payload do ESP32 via MQTT e persiste como leitura IoT",
            summary = "Endpoint MQTT — recebe LeituraIotRecord", tags = "Inserção de Informações")
    @PostMapping("/mqtt")
    public ResponseEntity<LeituraIotDTO> receberMqtt(@RequestBody @Valid LeituraIotRecord record) {
        EstacaoIot estacao = estacaoRepository.findById(record.idEstacao())
                .orElseThrow(() -> new RecursoNaoEncontradoException("EstacaoIot", record.idEstacao()));

        LeituraIot leitura = new LeituraIot();
        leitura.setNrTemperatura(record.nrTemperatura());
        leitura.setNrUmidade(record.nrUmidade());
        leitura.setNrChuvaMm(record.nrChuvaMm());
        leitura.setNrIndiceRisco(record.nrIndiceRisco());
        leitura.setEstacaoIot(estacao);

        LeituraIot salva = repository.save(leitura);
        cachingService.removerCache();
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toDTO(salva));
    }

    @Operation(description = "Inserir nova leitura IoT via DTO", summary = "Inserir LeituraIot", tags = "Inserção de Informações")
    @PostMapping("/novo")
    public ResponseEntity<LeituraIotDTO> inserir(@RequestBody @Valid LeituraIotDTO dto) {
        LeituraIot salvo = repository.save(mapper.toEntity(dto));
        cachingService.removerCache();
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toDTO(salvo));
    }

    @Operation(description = "Remover leitura IoT", summary = "Remover LeituraIot", tags = "Remoção de Informações")
    @DeleteMapping("/remover/{id}")
    public ResponseEntity<Void> remover(@PathVariable Long id) {
        LeituraIot entidade = repository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("LeituraIot", id));
        repository.delete(entidade);
        cachingService.removerCache();
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Operation(description = "Atualizar leitura IoT", summary = "Atualizar LeituraIot", tags = "Atualização de Informações")
    @PutMapping("/atualizar/{id}")
    public ResponseEntity<LeituraIotDTO> atualizar(@PathVariable Long id, @RequestBody @Valid LeituraIotDTO dto) {
        LeituraIot antigo = cachingService.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("LeituraIot", id));
        antigo.atualizar(mapper.toEntity(dto));
        repository.save(antigo);
        cachingService.removerCache();
        return ResponseEntity.ok(mapper.toDTO(antigo));
    }
}