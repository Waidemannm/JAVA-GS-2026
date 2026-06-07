package br.com.fiap.orbitAlert.service;

import br.com.fiap.orbitAlert.model.Alerta;
import br.com.fiap.orbitAlert.model.enums.StatusAlertaEnum;
import br.com.fiap.orbitAlert.repository.IAlertaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AlertaCachingService {

    @Autowired
    private IAlertaRepository iAlertaRepository;

    @Cacheable(value = "retornarTodosAlertasPaginados", key = "#pageRequest")
    public Page<Alerta> findAll(PageRequest pageRequest) {
        return iAlertaRepository.findAll(pageRequest);
    }

    @Cacheable(value = "retornarTodosAlertas")
    public List<Alerta> findAll() {
        return iAlertaRepository.findAll();
    }

    @Cacheable(value = "retornarAlertaPorId", key = "#id")
    public Optional<Alerta> findById(Long id) {
        return iAlertaRepository.findById(id);
    }

    @Cacheable(value = "retornarAlertasPorStatus", key = "#stStatus")
    public List<Alerta> findByStStatus(StatusAlertaEnum stStatus) {
        return iAlertaRepository.findByStStatus(stStatus);
    }

    @Cacheable(value = "retornarAlertasAtivosPorMunicipio", key = "#idMunicipio")
    public List<Alerta> buscarAtivosAgrupadosPorMunicipio(Long idMunicipio) {
        return iAlertaRepository.buscarAtivosAgrupadosPorMunicipio(idMunicipio);
    }

    @Cacheable(value = "retornarAlertasCriticos")
    public List<Alerta> buscarCriticosUltimos30Dias() {
        return iAlertaRepository.buscarCriticosUltimos30Dias();
    }

    @CacheEvict(value = {
            "retornarTodosAlertasPaginados", "retornarTodosAlertas",
            "retornarAlertaPorId", "retornarAlertasPorStatus",
            "retornarAlertasAtivosPorMunicipio", "retornarAlertasCriticos"
    }, allEntries = true)
    public void removerCache() {
        System.out.println("Cache de Alerta removido!");
    }
}