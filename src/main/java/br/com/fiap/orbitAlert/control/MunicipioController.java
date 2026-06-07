package br.com.fiap.orbitAlert.control;

import br.com.fiap.orbitAlert.config.RecursoNaoEncontradoException;
import br.com.fiap.orbitAlert.dto.MunicipioDTO;
import br.com.fiap.orbitAlert.mapper.IMunicipioMapper;
import br.com.fiap.orbitAlert.model.Municipio;
import br.com.fiap.orbitAlert.records.MunicipioRiscoRecord;
import br.com.fiap.orbitAlert.repository.IAlertaRepository;
import br.com.fiap.orbitAlert.repository.IEstacaoIotRepository;
import br.com.fiap.orbitAlert.repository.IMunicipioRepository;
import br.com.fiap.orbitAlert.service.MunicipioCachingService;
import br.com.fiap.orbitAlert.service.MunicipioPaginacaoService;
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
@RequestMapping(value = "/municipio")
public class MunicipioController {

    @Autowired private IMunicipioRepository repository;
    @Autowired private IMunicipioMapper mapper;
    @Autowired private MunicipioCachingService cachingService;
    @Autowired private MunicipioPaginacaoService paginacaoService;
    @Autowired private IAlertaRepository alertaRepository;
    @Autowired private IEstacaoIotRepository estacaoRepository;

    @Operation(description = "Retorna todos os municípios", summary = "Retorna MunicipioDTO", tags = "Retorno de Informações")
    @GetMapping("/todos")
    public ResponseEntity<List<MunicipioDTO>> retornarTodos() {
        return ResponseEntity.ok(cachingService.findAll().stream()
                .map(mapper::toDTO).collect(Collectors.toList()));
    }

    @Operation(description = "Retorna municípios paginados", summary = "Retorna MunicipioDTO paginado", tags = "Retorno de Informações")
    @GetMapping("/paginar")
    public ResponseEntity<Page<MunicipioDTO>> paginar(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "5") Integer size) {
        PageRequest request = PageRequest.of(page, size);
        return ResponseEntity.ok(paginacaoService.paginar(request).map(mapper::toDTO));
    }

    @Operation(description = "Retorna município por ID com HATEOAS", summary = "Retorna Municipio por ID", tags = "Retorno de Informações")
    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<MunicipioDTO>> retornarPorId(@PathVariable Long id) {
        Municipio municipio = cachingService.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Municipio", id));
        MunicipioDTO dto = mapper.toDTO(municipio);
        Link selfLink = linkTo(methodOn(MunicipioController.class).retornarPorId(id)).withSelfRel();
        Link todosLink = linkTo(methodOn(MunicipioController.class).retornarTodos()).withRel("todos");
        return ResponseEntity.ok(EntityModel.of(dto, selfLink, todosLink));
    }

    @Operation(description = "Dashboard de risco por município — retorna MunicipioRiscoRecord",
            summary = "Dashboard de risco", tags = "Retorno de Informações")
    @GetMapping("/dashboard")
    public ResponseEntity<List<MunicipioRiscoRecord>> retornarDashboard() {
        List<MunicipioRiscoRecord> dashboard = cachingService.findAll().stream()
                .filter(m -> m.getStAtivo().equals("S"))
                .map(m -> {
                    long alertasAtivos = alertaRepository
                            .buscarAtivosAgrupadosPorMunicipio(m.getId()).size();

                    double indicesMedio = m.getZonas() == null || m.getZonas().isEmpty() ? 0.0 :
                            m.getZonas().stream()
                                    .flatMap(z -> z.getAlertas() == null ?
                                            java.util.stream.Stream.empty() : z.getAlertas().stream())
                                    .mapToInt(a -> a.getNrNivelRisco())
                                    .average().orElse(0.0);

                    long estacoesAtivas = estacaoRepository
                            .buscarAtivasPorMunicipio(m.getId()).size();

                    return new MunicipioRiscoRecord(
                            m.getId(),
                            m.getNmMunicipio(),
                            m.getNmEstado(),
                            alertasAtivos,
                            Math.round(indicesMedio * 10.0) / 10.0,
                            estacoesAtivas
                    );
                })
                .collect(Collectors.toList());
        return ResponseEntity.ok(dashboard);
    }

    @Operation(description = "Retorna municípios com alertas ativos", summary = "Municípios com alertas", tags = "Retorno de Informações")
    @GetMapping("/com-alertas")
    public ResponseEntity<List<MunicipioDTO>> retornarComAlertasAtivos() {
        return ResponseEntity.ok(cachingService.buscarComAlertasAtivos().stream()
                .map(mapper::toDTO).collect(Collectors.toList()));
    }

    @Operation(description = "Inserir novo município", summary = "Inserir Municipio", tags = "Inserção de Informações")
    @PostMapping("/novo")
    public ResponseEntity<MunicipioDTO> inserir(@RequestBody @Valid MunicipioDTO dto) {
        Municipio salvo = repository.save(mapper.toEntity(dto));
        cachingService.removerCache();
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toDTO(salvo));
    }

    @Operation(description = "Remover município", summary = "Remover Municipio", tags = "Remoção de Informações")
    @DeleteMapping("/remover/{id}")
    public ResponseEntity<Void> remover(@PathVariable Long id) {
        Municipio municipio = repository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Municipio", id));
        repository.delete(municipio);
        cachingService.removerCache();
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Operation(description = "Atualizar município", summary = "Atualizar Municipio", tags = "Atualização de Informações")
    @PutMapping("/atualizar/{id}")
    public ResponseEntity<MunicipioDTO> atualizar(@PathVariable Long id, @RequestBody @Valid MunicipioDTO dto) {
        Municipio antigo = cachingService.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Municipio", id));
        antigo.atualizar(mapper.toEntity(dto));
        repository.save(antigo);
        cachingService.removerCache();
        return ResponseEntity.ok(mapper.toDTO(antigo));
    }
}