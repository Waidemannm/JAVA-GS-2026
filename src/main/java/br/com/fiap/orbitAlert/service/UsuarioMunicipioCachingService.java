package br.com.fiap.orbitAlert.service;

import br.com.fiap.orbitAlert.model.UsuarioMunicipio;
import br.com.fiap.orbitAlert.model.UsuarioMunicipioPK;
import br.com.fiap.orbitAlert.repository.IUsuarioMunicipioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioMunicipioCachingService {

    @Autowired
    private IUsuarioMunicipioRepository iUsuarioMunicipioRepository;

    @Cacheable(value = "retornarTodosUsuariosMunicipios")
    public List<UsuarioMunicipio> findAll() {
        return iUsuarioMunicipioRepository.findAll();
    }

    @Cacheable(value = "retornarUsuarioMunicipioPorId", key = "#id")
    public Optional<UsuarioMunicipio> findById(UsuarioMunicipioPK id) {
        return iUsuarioMunicipioRepository.findById(id);
    }

    @Cacheable(value = "retornarVinculosPorUsuario", key = "#idUsuario")
    public List<UsuarioMunicipio> findByUsuarioId(Long idUsuario) {
        return iUsuarioMunicipioRepository.findByUsuarioId(idUsuario);
    }

    @Cacheable(value = "retornarVinculosPorMunicipio", key = "#idMunicipio")
    public List<UsuarioMunicipio> findByMunicipioId(Long idMunicipio) {
        return iUsuarioMunicipioRepository.findByMunicipioId(idMunicipio);
    }

    @CacheEvict(value = {
            "retornarTodosUsuariosMunicipios",
            "retornarUsuarioMunicipioPorId",
            "retornarVinculosPorUsuario",
            "retornarVinculosPorMunicipio"
    }, allEntries = true)
    public void removerCache() {
        System.out.println("Cache de UsuarioMunicipio removido!");
    }
}