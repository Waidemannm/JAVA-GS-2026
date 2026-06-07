package br.com.fiap.orbitAlert.control;

import br.com.fiap.orbitAlert.dto.NotificacaoDTO;
import br.com.fiap.orbitAlert.mapper.INotificacaoMapper;
import br.com.fiap.orbitAlert.model.Notificacao;
import br.com.fiap.orbitAlert.repository.INotificacaoRepository;
import br.com.fiap.orbitAlert.service.NotificacaoCachingService;
import br.com.fiap.orbitAlert.service.NotificacaoPaginacaoService;
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
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping(value = "/notificacao")
public class NotificacaoController {

    @Autowired
    private INotificacaoRepository repository;
    @Autowired
    private INotificacaoMapper mapper;
    @Autowired
    private NotificacaoCachingService cachingService;
    @Autowired
    private NotificacaoPaginacaoService paginacaoService;

    @Operation(description = "Retorna todos os registros de Notificacao", summary = "Retorna NotificacaoDTO", tags = "Retorno de Informações")
    @GetMapping("/todos")
    public ResponseEntity<List<NotificacaoDTO>> retornarTodos() {
        return ResponseEntity.ok(cachingService.findAll().stream()
                .map(mapper::toDTO).collect(Collectors.toList()));
    }

    @Operation(description = "Retorna Notificacao paginado", summary = "Retorna NotificacaoDTO paginado", tags = "Retorno de Informações")
    @GetMapping("/paginar")
    public ResponseEntity<Page<NotificacaoDTO>> paginar(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "5") Integer size) {
        PageRequest request = PageRequest.of(page, size);
        return ResponseEntity.ok(paginacaoService.paginar(request).map(mapper::toDTO));
    }

    @Operation(description = "Retorna Notificacao por ID com HATEOAS", summary = "Retorna Notificacao por ID", tags = "Retorno de Informações")
    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<NotificacaoDTO>> retornarPorId(@PathVariable Long id) {
        Optional<Notificacao> op = cachingService.findById(id);
        if (op.isPresent()) {
            NotificacaoDTO dto = mapper.toDTO(op.get());
            Link selfLink = linkTo(methodOn(NotificacaoController.class).retornarPorId(id)).withSelfRel();
            Link todosLink = linkTo(methodOn(NotificacaoController.class).retornarTodos()).withRel("todos");
            return ResponseEntity.ok(EntityModel.of(dto, selfLink, todosLink));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(description = "Inserir novo Notificacao", summary = "Inserir Notificacao", tags = "Inserção de Informações")
    @PostMapping("/novo")
    public ResponseEntity<NotificacaoDTO> inserir(@RequestBody @Valid NotificacaoDTO dto) {
        Notificacao salvo = repository.save(mapper.toEntity(dto));
        cachingService.removerCache();
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toDTO(salvo));
    }

    @Operation(description = "Remover Notificacao", summary = "Remover Notificacao", tags = "Remoção de Informações")
    @DeleteMapping("/remover/{id}")
    public ResponseEntity<Void> remover(@PathVariable Long id) {
        Optional<Notificacao> op = repository.findById(id);
        if (op.isPresent()) {
            repository.delete(op.get());
            cachingService.removerCache();
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @Operation(description = "Atualizar Notificacao", summary = "Atualizar Notificacao", tags = "Atualização de Informações")
    @PutMapping("/atualizar/{id}")
    public ResponseEntity<NotificacaoDTO> atualizar(
            @PathVariable Long id,
            @RequestBody @Valid NotificacaoDTO dto) {
        Optional<Notificacao> op = cachingService.findById(id);
        if (op.isPresent()) {
            Notificacao antigo = op.get();
            Notificacao novo = mapper.toEntity(dto);
            antigo.atualizar(novo);
            repository.save(antigo);
            cachingService.removerCache();
            return ResponseEntity.status(HttpStatus.OK).body(mapper.toDTO(antigo));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}