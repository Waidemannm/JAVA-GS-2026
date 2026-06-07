package br.com.fiap.orbitAlert.service;

import br.com.fiap.orbitAlert.model.Municipio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MunicipioPaginacaoService {

    @Autowired
    private MunicipioCachingService municipioCachingService;

    @Transactional(readOnly = true)
    public Page<Municipio> paginar(PageRequest pageRequest) {
        return municipioCachingService.findAll(pageRequest);
    }
}