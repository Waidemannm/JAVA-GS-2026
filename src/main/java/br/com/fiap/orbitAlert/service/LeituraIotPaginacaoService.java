package br.com.fiap.orbitAlert.service;

import br.com.fiap.orbitAlert.model.LeituraIot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LeituraIotPaginacaoService {

    @Autowired
    private LeituraIotCachingService leituraiotCachingService;

    @Transactional(readOnly = true)
    public Page<LeituraIot> paginar(PageRequest pageRequest) {
        return leituraiotCachingService.findAll(pageRequest);
    }
}