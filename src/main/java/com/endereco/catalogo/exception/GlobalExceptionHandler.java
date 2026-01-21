package com.endereco.catalogo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * Manipulador global de exceções da aplicação.
 * 
 * Esta classe captura exceções em toda a aplicação e retorna
 * respostas HTTP padronizadas com mensagens de erro informativas.
 * Utiliza @RestControllerAdvice para centralizador o tratamento de erros.
 * 
 * @author Treinamento Spring Boot
 * @version 1.0
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Manipula exceções de validação (@Valid).
     * 
     * Quando uma validação de entidade falha (constraints como @NotBlank, @Size),
     * este método é acionado automaticamente e retorna um response com status 400.
     * 
     * @param ex A exceção de validação
     * @return ResponseEntity com status 400 Bad Request e detalhes dos erros
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationException(
            MethodArgumentNotValidException ex) {
        
        // Cria mapa com informações do erro
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("timestamp", LocalDateTime.now());
        errorResponse.put("status", HttpStatus.BAD_REQUEST.value());
        errorResponse.put("error", "Validação Falhou");
        errorResponse.put("message", "Dados inválidos fornecidos");
        
        // Extrai detalhes dos erros de validação
        Map<String, String> fieldErrors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
            fieldErrors.put(
                error.getField(),
                error.getDefaultMessage()
            )
        );
        
        errorResponse.put("fields", fieldErrors);
        
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(errorResponse);
    }

    /**
     * Manipula exceções IllegalArgumentException.
     * 
     * Quando o service lança IllegalArgumentException (ex: usuário não encontrado),
     * este método captura e retorna um response com status 400.
     * 
     * @param ex A exceção de argumento inválido
     * @return ResponseEntity com status 400 Bad Request
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, Object>> handleIllegalArgumentException(
            IllegalArgumentException ex) {
        
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("timestamp", LocalDateTime.now());
        errorResponse.put("status", HttpStatus.BAD_REQUEST.value());
        errorResponse.put("error", "Argumento Inválido");
        errorResponse.put("message", ex.getMessage());
        
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(errorResponse);
    }

    /**
     * Manipula exceções genéricas não capturadas.
     * 
     * Fallback para qualquer exceção não tratada especificamente.
     * Retorna status 500 Internal Server Error.
     * 
     * @param ex A exceção genérica
     * @return ResponseEntity com status 500 Internal Server Error
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGenericException(
            Exception ex) {
        
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("timestamp", LocalDateTime.now());
        errorResponse.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
        errorResponse.put("error", "Erro Interno do Servidor");
        errorResponse.put("message", "Ocorreu um erro não previsto. Tente novamente mais tarde.");
        
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(errorResponse);
    }
}
