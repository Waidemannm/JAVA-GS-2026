package br.com.fiap.orbitAlert.service;

import br.com.fiap.orbitAlert.model.LeituraIot;
import br.com.fiap.orbitAlert.repository.ILeituraIotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LeituraIotCachingService {

    @Autowired
    private ILeituraIotRepository iLeituraIotRepository;

    @Cacheable(value = "retornarTodosLeituraIotsPaginados", key = "#pageRequest")
    public Page<LeituraIot> findAll(PageRequest pageRequest) {
        return iLeituraIotRepository.findAll(pageRequest);
    }

    @Cacheable(value = "retornarTodosLeituraIots")
    public List<LeituraIot> findAll() {
        return iLeituraIotRepository.findAll();
    }

    @Cacheable(value = "retornarLeituraIotPorId", key = "#id")
    public Optional<LeituraIot> findById(Long id) {
        return iLeituraIotRepository.findById(id);
    }

    @CacheEvict(value = {
            "retornarTodosLeituraIotsPaginados", "retornarTodosLeituraIots",
            "retornarLeituraIotPorId"
    }, allEntries = true)
    public void removerCache() {
        System.out.println("Cache de LeituraIot removido!");
    }
}