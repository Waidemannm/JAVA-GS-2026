package br.com.fiap.orbitAlert.service;

import br.com.fiap.orbitAlert.model.TipoAlerta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TipoAlertaPaginacaoService {

    @Autowired
    private TipoAlertaCachingService tipoalertaCachingService;

    @Transactional(readOnly = true)
    public Page<TipoAlerta> paginar(PageRequest pageRequest) {
        return tipoalertaCachingService.findAll(pageRequest);
    }
}