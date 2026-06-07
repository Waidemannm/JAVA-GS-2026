package br.com.fiap.orbitAlert.service;

import br.com.fiap.orbitAlert.model.AnaliseIa;
import br.com.fiap.orbitAlert.repository.IAnaliseIaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AnaliseIaCachingService {

    @Autowired
    private IAnaliseIaRepository iAnaliseIaRepository;

    @Cacheable(value = "retornarTodosAnaliseIasPaginados", key = "#pageRequest")
    public Page<AnaliseIa> findAll(PageRequest pageRequest) {
        return iAnaliseIaRepository.findAll(pageRequest);
    }

    @Cacheable(value = "retornarTodosAnaliseIas")
    public List<AnaliseIa> findAll() {
        return iAnaliseIaRepository.findAll();
    }

    @Cacheable(value = "retornarAnaliseIaPorId", key = "#id")
    public Optional<AnaliseIa> findById(Long id) {
        return iAnaliseIaRepository.findById(id);
    }

    @CacheEvict(value = {
            "retornarTodosAnaliseIasPaginados", "retornarTodosAnaliseIas",
            "retornarAnaliseIaPorId"
    }, allEntries = true)
    public void removerCache() {
        System.out.println("Cache de AnaliseIa removido!");
    }
}