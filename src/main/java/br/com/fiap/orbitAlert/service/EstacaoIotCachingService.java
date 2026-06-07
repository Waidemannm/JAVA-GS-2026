package br.com.fiap.orbitAlert.service;

import br.com.fiap.orbitAlert.model.EstacaoIot;
import br.com.fiap.orbitAlert.repository.IEstacaoIotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EstacaoIotCachingService {

    @Autowired
    private IEstacaoIotRepository iEstacaoIotRepository;

    @Cacheable(value = "retornarTodosEstacaoIotsPaginados", key = "#pageRequest")
    public Page<EstacaoIot> findAll(PageRequest pageRequest) {
        return iEstacaoIotRepository.findAll(pageRequest);
    }

    @Cacheable(value = "retornarTodosEstacaoIots")
    public List<EstacaoIot> findAll() {
        return iEstacaoIotRepository.findAll();
    }

    @Cacheable(value = "retornarEstacaoIotPorId", key = "#id")
    public Optional<EstacaoIot> findById(Long id) {
        return iEstacaoIotRepository.findById(id);
    }

    @CacheEvict(value = {
            "retornarTodosEstacaoIotsPaginados", "retornarTodosEstacaoIots",
            "retornarEstacaoIotPorId"
    }, allEntries = true)
    public void removerCache() {
        System.out.println("Cache de EstacaoIot removido!");
    }
}