package br.com.fiap.orbitAlert.service;

import br.com.fiap.orbitAlert.model.UsuarioMunicipio;
import br.com.fiap.orbitAlert.repository.IUsuarioMunicipioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UsuarioMunicipioPaginacaoService {

    @Autowired
    private IUsuarioMunicipioRepository iUsuarioMunicipioRepository;

    @Transactional(readOnly = true)
    public Page<UsuarioMunicipio> paginar(PageRequest pageRequest) {
        return iUsuarioMunicipioRepository.findAll(pageRequest);
    }
}