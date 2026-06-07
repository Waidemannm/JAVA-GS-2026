package br.com.fiap.orbitAlert.service;

import br.com.fiap.orbitAlert.model.Usuario;
import br.com.fiap.orbitAlert.model.enums.TipoPerfilEnum;
import br.com.fiap.orbitAlert.repository.IUsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioCachingService {

    @Autowired
    private IUsuarioRepository iUsuarioRepository;

    @Cacheable(value = "retornarTodosUsuariosPaginados", key = "#pageRequest")
    public Page<Usuario> findAll(PageRequest pageRequest) {
        return iUsuarioRepository.findAll(pageRequest);
    }

    @Cacheable(value = "retornarTodosUsuarios")
    public List<Usuario> findAll() {
        return iUsuarioRepository.findAll();
    }

    @Cacheable(value = "retornarUsuarioPorId", key = "#id")
    public Optional<Usuario> findById(Long id) {
        return iUsuarioRepository.findById(id);
    }

    @Cacheable(value = "retornarUsuariosPorPerfil", key = "#tpPerfil")
    public List<Usuario> findByTpPerfil(TipoPerfilEnum tpPerfil) {
        return iUsuarioRepository.findByTpPerfil(tpPerfil);
    }

    @Cacheable(value = "retornarUsuariosAtivosPorMunicipio", key = "#idMunicipio")
    public List<Usuario> buscarAtivosPorMunicipio(Long idMunicipio) {
        return iUsuarioRepository.buscarAtivosPorMunicipio(idMunicipio);
    }

    @CacheEvict(value = {
            "retornarTodosUsuariosPaginados", "retornarTodosUsuarios",
            "retornarUsuarioPorId", "retornarUsuariosPorPerfil",
            "retornarUsuariosAtivosPorMunicipio"
    }, allEntries = true)
    public void removerCache() {
        System.out.println("Cache de Usuario removido!");
    }
}