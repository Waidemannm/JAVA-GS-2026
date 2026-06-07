package br.com.fiap.orbitAlert.service;

import br.com.fiap.orbitAlert.model.HistoricoAlerta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class HistoricoPaginacaoService {

    @Autowired
    private HistoricoCachingService historicoalertaCachingService;

    @Transactional(readOnly = true)
    public Page<HistoricoAlerta> paginar(PageRequest pageRequest) {
        return historicoalertaCachingService.findAll(pageRequest);
    }
}