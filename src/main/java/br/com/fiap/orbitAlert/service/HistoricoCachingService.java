package br.com.fiap.orbitAlert.service;

import br.com.fiap.orbitAlert.model.HistoricoAlerta;
import br.com.fiap.orbitAlert.repository.IHistoricoAlertaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class HistoricoCachingService {

    @Autowired
    private IHistoricoAlertaRepository iHistoricoAlertaRepository;

    @Cacheable(value = "retornarTodosHistoricoAlertasPaginados", key = "#pageRequest")
    public Page<HistoricoAlerta> findAll(PageRequest pageRequest) {
        return iHistoricoAlertaRepository.findAll(pageRequest);
    }

    @Cacheable(value = "retornarTodosHistoricoAlertas")
    public List<HistoricoAlerta> findAll() {
        return iHistoricoAlertaRepository.findAll();
    }

    @Cacheable(value = "retornarHistoricoAlertaPorId", key = "#id")
    public Optional<HistoricoAlerta> findById(Long id) {
        return iHistoricoAlertaRepository.findById(id);
    }

    @CacheEvict(value = {
            "retornarTodosHistoricoAlertasPaginados", "retornarTodosHistoricoAlertas",
            "retornarHistoricoAlertaPorId"
    }, allEntries = true)
    public void removerCache() {
        System.out.println("Cache de HistoricoAlerta removido!");
    }
}