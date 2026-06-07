package br.com.fiap.orbitAlert.service;

import br.com.fiap.orbitAlert.model.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UsuarioPaginacaoService {

    @Autowired
    private UsuarioCachingService usuarioCachingService;

    @Transactional(readOnly = true)
    public Page<Usuario> paginar(PageRequest pageRequest) {
        return usuarioCachingService.findAll(pageRequest);
    }
}