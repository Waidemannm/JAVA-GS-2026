package br.com.fiap.orbitAlert.service;

import br.com.fiap.orbitAlert.model.Notificacao;
import br.com.fiap.orbitAlert.repository.INotificacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NotificacaoCachingService {

    @Autowired
    private INotificacaoRepository iNotificacaoRepository;

    @Cacheable(value = "retornarTodosNotificacaosPaginados", key = "#pageRequest")
    public Page<Notificacao> findAll(PageRequest pageRequest) {
        return iNotificacaoRepository.findAll(pageRequest);
    }

    @Cacheable(value = "retornarTodosNotificacaos")
    public List<Notificacao> findAll() {
        return iNotificacaoRepository.findAll();
    }

    @Cacheable(value = "retornarNotificacaoPorId", key = "#id")
    public Optional<Notificacao> findById(Long id) {
        return iNotificacaoRepository.findById(id);
    }

    @CacheEvict(value = {
            "retornarTodosNotificacaosPaginados", "retornarTodosNotificacaos",
            "retornarNotificacaoPorId"
    }, allEntries = true)
    public void removerCache() {
        System.out.println("Cache de Notificacao removido!");
    }
}