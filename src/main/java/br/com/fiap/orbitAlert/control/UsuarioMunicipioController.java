package br.com.fiap.orbitAlert.control;

import br.com.fiap.orbitAlert.dto.UsuarioMunicipioDTO;
import br.com.fiap.orbitAlert.mapper.IUsuarioMunicipioMapper;
import br.com.fiap.orbitAlert.model.UsuarioMunicipio;
import br.com.fiap.orbitAlert.model.UsuarioMunicipioPK;
import br.com.fiap.orbitAlert.repository.IUsuarioMunicipioRepository;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/usuario-municipio")
public class UsuarioMunicipioController {

    @Autowired
    private IUsuarioMunicipioRepository repository;
    @Autowired
    private IUsuarioMunicipioMapper mapper;

    @Operation(description = "Retorna todos os vínculos usuário-município", summary = "Retorna vínculos", tags = "Retorno de Informações")
    @GetMapping("/todos")
    public ResponseEntity<List<UsuarioMunicipioDTO>> retornarTodos() {
        return ResponseEntity.ok(repository.findAll().stream()
                .map(mapper::toDTO).collect(Collectors.toList()));
    }

    @Operation(description = "Retorna vínculos de um usuário", summary = "Vínculos por usuário", tags = "Retorno de Informações")
    @GetMapping("/usuario/{idUsuario}")
    public ResponseEntity<List<UsuarioMunicipioDTO>> retornarPorUsuario(@PathVariable Long idUsuario) {
        return ResponseEntity.ok(repository.findByUsuarioId(idUsuario).stream()
                .map(mapper::toDTO).collect(Collectors.toList()));
    }

    @Operation(description = "Retorna vínculos de um município", summary = "Vínculos por município", tags = "Retorno de Informações")
    @GetMapping("/municipio/{idMunicipio}")
    public ResponseEntity<List<UsuarioMunicipioDTO>> retornarPorMunicipio(@PathVariable Long idMunicipio) {
        return ResponseEntity.ok(repository.findByMunicipioId(idMunicipio).stream()
                .map(mapper::toDTO).collect(Collectors.toList()));
    }

    @Operation(description = "Criar vínculo usuário-município", summary = "Inserir vínculo", tags = "Inserção de Informações")
    @PostMapping("/novo")
    public ResponseEntity<UsuarioMunicipioDTO> inserir(@RequestBody @Valid UsuarioMunicipioDTO dto) {
        UsuarioMunicipio salvo = repository.save(mapper.toEntity(dto));
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toDTO(salvo));
    }

    @Operation(description = "Remover vínculo usuário-município", summary = "Remover vínculo", tags = "Remoção de Informações")
    @DeleteMapping("/remover/{idUsuario}/{idMunicipio}")
    public ResponseEntity<Void> remover(@PathVariable Long idUsuario, @PathVariable Long idMunicipio) {
        UsuarioMunicipioPK pk = new UsuarioMunicipioPK(idUsuario, idMunicipio);
        Optional<UsuarioMunicipio> op = repository.findById(pk);
        if (op.isPresent()) {
            repository.delete(op.get());
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}