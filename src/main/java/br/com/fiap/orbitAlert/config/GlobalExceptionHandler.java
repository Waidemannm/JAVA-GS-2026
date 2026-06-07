package br.com.fiap.orbitAlert.config;

import br.com.fiap.orbitAlert.records.ErroResponseRecord;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationErrors(
            MethodArgumentNotValidException ex) {

        Map<String, String> erros = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String campo = ((FieldError) error).getField();
            String mensagem = error.getDefaultMessage();
            erros.put(campo, mensagem);
        });

        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now().toString());
        body.put("status", HttpStatus.BAD_REQUEST.value());
        body.put("erro", "Validação de dados falhou");
        body.put("campos", erros);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }


    @ExceptionHandler(RecursoNaoEncontradoException.class)
    public ResponseEntity<ErroResponseRecord> handleRecursoNaoEncontrado(
            RecursoNaoEncontradoException ex) {

        ErroResponseRecord record = new ErroResponseRecord(
                LocalDateTime.now().toString(),
                HttpStatus.NOT_FOUND.value(),
                "Recurso não encontrado",
                ex.getMessage()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(record);
    }


    @ExceptionHandler(RegraDeNegocioException.class)
    public ResponseEntity<ErroResponseRecord> handleRegraDeNegocio(
            RegraDeNegocioException ex) {

        ErroResponseRecord record = new ErroResponseRecord(
                LocalDateTime.now().toString(),
                HttpStatus.UNPROCESSABLE_ENTITY.value(),
                "Regra de negócio violada",
                ex.getMessage()
        );
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(record);
    }


    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErroResponseRecord> handleIllegalArgument(
            IllegalArgumentException ex) {

        ErroResponseRecord record = new ErroResponseRecord(
                LocalDateTime.now().toString(),
                HttpStatus.BAD_REQUEST.value(),
                "Argumento inválido",
                ex.getMessage()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(record);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErroResponseRecord> handleGenerico(Exception ex) {

        ErroResponseRecord record = new ErroResponseRecord(
                LocalDateTime.now().toString(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Erro interno do servidor",
                ex.getMessage()
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(record);
    }
}