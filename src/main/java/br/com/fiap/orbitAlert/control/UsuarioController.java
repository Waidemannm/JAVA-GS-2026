package br.com.fiap.orbitAlert.control;

import br.com.fiap.orbitAlert.config.RecursoNaoEncontradoException;
import br.com.fiap.orbitAlert.dto.UsuarioDTO;
import br.com.fiap.orbitAlert.mapper.IUsuarioMapper;
import br.com.fiap.orbitAlert.model.Usuario;
import br.com.fiap.orbitAlert.model.enums.TipoPerfilEnum;
import br.com.fiap.orbitAlert.repository.IUsuarioRepository;
import br.com.fiap.orbitAlert.service.UsuarioCachingService;
import br.com.fiap.orbitAlert.service.UsuarioPaginacaoService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping(value = "/usuario")
public class UsuarioController {

    @Autowired private IUsuarioRepository repository;
    @Autowired private IUsuarioMapper mapper;
    @Autowired private UsuarioCachingService cachingService;
    @Autowired private UsuarioPaginacaoService paginacaoService;
    @Autowired private PasswordEncoder passwordEncoder;

    @Operation(description = "Retorna todos os usuários", summary = "Retorna UsuarioDTO", tags = "Retorno de Informações")
    @GetMapping("/todos")
    public ResponseEntity<List<UsuarioDTO>> retornarTodos() {
        return ResponseEntity.ok(cachingService.findAll().stream()
                .map(mapper::toDTO).collect(Collectors.toList()));
    }

    @Operation(description = "Retorna usuários paginados", summary = "Retorna UsuarioDTO paginado", tags = "Retorno de Informações")
    @GetMapping("/paginar")
    public ResponseEntity<Page<UsuarioDTO>> paginar(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "5") Integer size) {
        PageRequest request = PageRequest.of(page, size);
        return ResponseEntity.ok(paginacaoService.paginar(request).map(mapper::toDTO));
    }

    @Operation(description = "Retorna usuário por ID com HATEOAS", summary = "Retorna Usuário por ID", tags = "Retorno de Informações")
    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<UsuarioDTO>> retornarPorId(@PathVariable Long id) {
        Usuario usuario = cachingService.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Usuario", id));
        UsuarioDTO dto = mapper.toDTO(usuario);
        Link selfLink = linkTo(methodOn(UsuarioController.class).retornarPorId(id)).withSelfRel();
        Link todosLink = linkTo(methodOn(UsuarioController.class).retornarTodos()).withRel("todos");
        return ResponseEntity.ok(EntityModel.of(dto, selfLink, todosLink));
    }

    @Operation(description = "Retorna usuários por perfil", summary = "Usuários por perfil", tags = "Retorno de Informações")
    @GetMapping("/perfil")
    public ResponseEntity<List<UsuarioDTO>> retornarPorPerfil(@RequestParam TipoPerfilEnum tpPerfil) {
        return ResponseEntity.ok(cachingService.findByTpPerfil(tpPerfil).stream()
                .map(mapper::toDTO).collect(Collectors.toList()));
    }

    @Operation(description = "Retorna usuários ativos de um município", summary = "Usuários por município", tags = "Retorno de Informações")
    @GetMapping("/municipio/{idMunicipio}")
    public ResponseEntity<List<UsuarioDTO>> retornarAtivosPorMunicipio(@PathVariable Long idMunicipio) {
        return ResponseEntity.ok(cachingService.buscarAtivosPorMunicipio(idMunicipio).stream()
                .map(mapper::toDTO).collect(Collectors.toList()));
    }

    @Operation(description = "Inserir novo usuário — senha é encodada com BCrypt automaticamente",
            summary = "Inserir Usuário", tags = "Inserção de Informações")
    @PostMapping("/novo")
    public ResponseEntity<UsuarioDTO> inserir(@RequestBody @Valid UsuarioDTO dto) {
        Usuario entidade = mapper.toEntity(dto);
        entidade.setDsSenhaHash(passwordEncoder.encode(dto.getDsSenhaHash()));
        Usuario salvo = repository.save(entidade);
        cachingService.removerCache();
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toDTO(salvo));
    }

    @Operation(description = "Remover usuário", summary = "Remover Usuário", tags = "Remoção de Informações")
    @DeleteMapping("/remover/{id}")
    public ResponseEntity<Void> remover(@PathVariable Long id) {
        Usuario usuario = repository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Usuario", id));
        repository.delete(usuario);
        cachingService.removerCache();
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Operation(description = "Atualizar usuário — senha é re-encodada com BCrypt automaticamente",
            summary = "Atualizar Usuário", tags = "Atualização de Informações")
    @PutMapping("/atualizar/{id}")
    public ResponseEntity<UsuarioDTO> atualizar(@PathVariable Long id, @RequestBody @Valid UsuarioDTO dto) {
        Usuario antigo = cachingService.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Usuario", id));
        Usuario novo = mapper.toEntity(dto);
        novo.setDsSenhaHash(passwordEncoder.encode(dto.getDsSenhaHash()));
        antigo.atualizar(novo);
        repository.save(antigo);
        cachingService.removerCache();
        return ResponseEntity.ok(mapper.toDTO(antigo));
    }
}