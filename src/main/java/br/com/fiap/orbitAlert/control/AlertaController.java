package br.com.fiap.orbitAlert.control;

import br.com.fiap.orbitAlert.config.RecursoNaoEncontradoException;
import br.com.fiap.orbitAlert.dto.AlertaDTO;
import br.com.fiap.orbitAlert.mapper.IAlertaMapper;
import br.com.fiap.orbitAlert.model.Alerta;
import br.com.fiap.orbitAlert.model.enums.StatusAlertaEnum;
import br.com.fiap.orbitAlert.records.AlertaResumoRecord;
import br.com.fiap.orbitAlert.repository.IAlertaRepository;
import br.com.fiap.orbitAlert.service.AlertaCachingService;
import br.com.fiap.orbitAlert.service.AlertaPaginacaoService;
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
@RequestMapping(value = "/alerta")
public class AlertaController {

    @Autowired private IAlertaRepository repository;
    @Autowired private IAlertaMapper mapper;
    @Autowired private AlertaCachingService cachingService;
    @Autowired private AlertaPaginacaoService paginacaoService;

    @Operation(description = "Retorna todos os alertas", summary = "Retorna AlertaDTO", tags = "Retorno de Informações")
    @GetMapping("/todos")
    public ResponseEntity<List<AlertaDTO>> retornarTodos() {
        return ResponseEntity.ok(cachingService.findAll().stream()
                .map(mapper::toDTO).collect(Collectors.toList()));
    }

    @Operation(description = "Retorna alertas paginados", summary = "Retorna AlertaDTO paginado", tags = "Retorno de Informações")
    @GetMapping("/paginar")
    public ResponseEntity<Page<AlertaDTO>> paginar(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "5") Integer size) {
        PageRequest request = PageRequest.of(page, size);
        return ResponseEntity.ok(paginacaoService.paginar(request).map(mapper::toDTO));
    }

    @Operation(description = "Retorna alerta por ID com HATEOAS", summary = "Retorna Alerta por ID", tags = "Retorno de Informações")
    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<AlertaDTO>> retornarPorId(@PathVariable Long id) {
        Alerta alerta = cachingService.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Alerta", id));
        AlertaDTO dto = mapper.toDTO(alerta);
        Link selfLink = linkTo(methodOn(AlertaController.class).retornarPorId(id)).withSelfRel();
        Link todosLink = linkTo(methodOn(AlertaController.class).retornarTodos()).withRel("todos");
        return ResponseEntity.ok(EntityModel.of(dto, selfLink, todosLink));
    }

    @Operation(description = "Retorna resumo dos alertas como Record — leve para o app mobile",
            summary = "Resumo de alertas", tags = "Retorno de Informações")
    @GetMapping("/resumo")
    public ResponseEntity<List<AlertaResumoRecord>> retornarResumo() {
        List<AlertaResumoRecord> resumo = cachingService.findAll().stream()
                .map(a -> new AlertaResumoRecord(
                        a.getId(),
                        a.getZonaRisco().getNmZona(),
                        a.getZonaRisco().getMunicipio().getNmMunicipio(),
                        a.getTipoAlerta().getNmTipo(),
                        a.getNrNivelRisco(),
                        a.getStStatus(),
                        a.getDtCriacao()
                ))
                .collect(Collectors.toList());
        return ResponseEntity.ok(resumo);
    }

    @Operation(description = "Retorna alertas por status", summary = "Alertas por status", tags = "Retorno de Informações")
    @GetMapping("/status")
    public ResponseEntity<List<AlertaDTO>> retornarPorStatus(@RequestParam StatusAlertaEnum stStatus) {
        return ResponseEntity.ok(cachingService.findByStStatus(stStatus).stream()
                .map(mapper::toDTO).collect(Collectors.toList()));
    }

    @Operation(description = "Retorna alertas ativos por município", summary = "Alertas ativos por município", tags = "Retorno de Informações")
    @GetMapping("/municipio/{idMunicipio}")
    public ResponseEntity<List<AlertaDTO>> retornarAtivosPorMunicipio(@PathVariable Long idMunicipio) {
        return ResponseEntity.ok(cachingService.buscarAtivosAgrupadosPorMunicipio(idMunicipio).stream()
                .map(mapper::toDTO).collect(Collectors.toList()));
    }

    @Operation(description = "Retorna alertas críticos dos últimos 30 dias", summary = "Alertas críticos", tags = "Retorno de Informações")
    @GetMapping("/criticos")
    public ResponseEntity<List<AlertaDTO>> retornarCriticos() {
        return ResponseEntity.ok(cachingService.buscarCriticosUltimos30Dias().stream()
                .map(mapper::toDTO).collect(Collectors.toList()));
    }

    @Operation(description = "Inserir novo alerta", summary = "Inserir Alerta", tags = "Inserção de Informações")
    @PostMapping("/novo")
    public ResponseEntity<AlertaDTO> inserir(@RequestBody @Valid AlertaDTO dto) {
        Alerta salvo = repository.save(mapper.toEntity(dto));
        cachingService.removerCache();
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toDTO(salvo));
    }

    @Operation(description = "Remover alerta", summary = "Remover Alerta", tags = "Remoção de Informações")
    @DeleteMapping("/remover/{id}")
    public ResponseEntity<Void> remover(@PathVariable Long id) {
        Alerta alerta = repository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Alerta", id));
        repository.delete(alerta);
        cachingService.removerCache();
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Operation(description = "Atualizar alerta", summary = "Atualizar Alerta", tags = "Atualização de Informações")
    @PutMapping("/atualizar/{id}")
    public ResponseEntity<AlertaDTO> atualizar(@PathVariable Long id, @RequestBody @Valid AlertaDTO dto) {
        Alerta antigo = cachingService.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Alerta", id));
        antigo.atualizar(mapper.toEntity(dto));
        repository.save(antigo);
        cachingService.removerCache();
        return ResponseEntity.ok(mapper.toDTO(antigo));
    }
}