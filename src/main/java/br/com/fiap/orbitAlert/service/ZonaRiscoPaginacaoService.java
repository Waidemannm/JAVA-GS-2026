package br.com.fiap.orbitAlert.service;

import br.com.fiap.orbitAlert.model.ZonaRisco;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ZonaRiscoPaginacaoService {

    @Autowired
    private ZonaRiscoCachingService zonariscoCachingService;

    @Transactional(readOnly = true)
    public Page<ZonaRisco> paginar(PageRequest pageRequest) {
        return zonariscoCachingService.findAll(pageRequest);
    }
}