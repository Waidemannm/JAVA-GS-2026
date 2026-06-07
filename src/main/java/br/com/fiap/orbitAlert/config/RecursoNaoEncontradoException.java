package br.com.fiap.orbitAlert.config;

public class RecursoNaoEncontradoException extends RuntimeException {

    public RecursoNaoEncontradoException(String mensagem) {
        super(mensagem);
    }

    public RecursoNaoEncontradoException(String entidade, Long id) {
        super(entidade + " com ID " + id + " não encontrado.");
    }
}