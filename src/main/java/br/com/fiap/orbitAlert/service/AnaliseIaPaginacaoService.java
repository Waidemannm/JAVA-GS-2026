package br.com.fiap.orbitAlert.service;

import br.com.fiap.orbitAlert.model.AnaliseIa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AnaliseIaPaginacaoService {

    @Autowired
    private AnaliseIaCachingService analiseiaCachingService;

    @Transactional(readOnly = true)
    public Page<AnaliseIa> paginar(PageRequest pageRequest) {
        return analiseiaCachingService.findAll(pageRequest);
    }
}