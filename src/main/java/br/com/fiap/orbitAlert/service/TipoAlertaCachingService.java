package br.com.fiap.orbitAlert.service;

import br.com.fiap.orbitAlert.model.TipoAlerta;
import br.com.fiap.orbitAlert.repository.ITipoAlertaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TipoAlertaCachingService {

    @Autowired
    private ITipoAlertaRepository iTipoAlertaRepository;

    @Cacheable(value = "retornarTodosTipoAlertasPaginados", key = "#pageRequest")
    public Page<TipoAlerta> findAll(PageRequest pageRequest) {
        return iTipoAlertaRepository.findAll(pageRequest);
    }

    @Cacheable(value = "retornarTodosTipoAlertas")
    public List<TipoAlerta> findAll() {
        return iTipoAlertaRepository.findAll();
    }

    @Cacheable(value = "retornarTipoAlertaPorId", key = "#id")
    public Optional<TipoAlerta> findById(Long id) {
        return iTipoAlertaRepository.findById(id);
    }

    @CacheEvict(value = {
            "retornarTodosTipoAlertasPaginados", "retornarTodosTipoAlertas",
            "retornarTipoAlertaPorId"
    }, allEntries = true)
    public void removerCache() {
        System.out.println("Cache de TipoAlerta removido!");
    }
}