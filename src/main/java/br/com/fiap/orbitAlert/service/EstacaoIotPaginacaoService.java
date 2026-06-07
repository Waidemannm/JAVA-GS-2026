package br.com.fiap.orbitAlert.service;

import br.com.fiap.orbitAlert.model.EstacaoIot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EstacaoIotPaginacaoService {

    @Autowired
    private EstacaoIotCachingService estacaoiotCachingService;

    @Transactional(readOnly = true)
    public Page<EstacaoIot> paginar(PageRequest pageRequest) {
        return estacaoiotCachingService.findAll(pageRequest);
    }
}