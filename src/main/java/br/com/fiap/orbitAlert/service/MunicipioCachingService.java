package br.com.fiap.orbitAlert.service;

import br.com.fiap.orbitAlert.model.Municipio;
import br.com.fiap.orbitAlert.repository.IMunicipioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MunicipioCachingService {

    @Autowired
    private IMunicipioRepository iMunicipioRepository;

    @Cacheable(value = "retornarTodosMunicipiosPaginados", key = "#pageRequest")
    public Page<Municipio> findAll(PageRequest pageRequest) {
        return iMunicipioRepository.findAll(pageRequest);
    }

    @Cacheable(value = "retornarTodosMunicipios")
    public List<Municipio> findAll() {
        return iMunicipioRepository.findAll();
    }

    @Cacheable(value = "retornarMunicipioPorId", key = "#id")
    public Optional<Municipio> findById(Long id) {
        return iMunicipioRepository.findById(id);
    }

    @Cacheable(value = "retornarMunicipiosPorEstado", key = "#nmEstado")
    public List<Municipio> findByNmEstado(String nmEstado) {
        return iMunicipioRepository.findByNmEstado(nmEstado);
    }

    @Cacheable(value = "retornarMunicipiosComAlertasAtivos")
    public List<Municipio> buscarComAlertasAtivos() {
        return iMunicipioRepository.buscarComAlertasAtivos();
    }

    @CacheEvict(value = {
            "retornarTodosMunicipiosPaginados", "retornarTodosMunicipios",
            "retornarMunicipioPorId", "retornarMunicipiosPorEstado",
            "retornarMunicipiosComAlertasAtivos"
    }, allEntries = true)
    public void removerCache() {
        System.out.println("Cache de Municipio removido!");
    }
}