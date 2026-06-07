package br.com.fiap.orbitAlert.service;

import br.com.fiap.orbitAlert.model.ZonaRisco;
import br.com.fiap.orbitAlert.repository.IZonaRiscoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ZonaRiscoCachingService {

    @Autowired
    private IZonaRiscoRepository iZonaRiscoRepository;

    @Cacheable(value = "retornarTodosZonaRiscosPaginados", key = "#pageRequest")
    public Page<ZonaRisco> findAll(PageRequest pageRequest) {
        return iZonaRiscoRepository.findAll(pageRequest);
    }

    @Cacheable(value = "retornarTodosZonaRiscos")
    public List<ZonaRisco> findAll() {
        return iZonaRiscoRepository.findAll();
    }

    @Cacheable(value = "retornarZonaRiscoPorId", key = "#id")
    public Optional<ZonaRisco> findById(Long id) {
        return iZonaRiscoRepository.findById(id);
    }

    @CacheEvict(value = {
            "retornarTodosZonaRiscosPaginados", "retornarTodosZonaRiscos",
            "retornarZonaRiscoPorId"
    }, allEntries = true)
    public void removerCache() {
        System.out.println("Cache de ZonaRisco removido!");
    }
}