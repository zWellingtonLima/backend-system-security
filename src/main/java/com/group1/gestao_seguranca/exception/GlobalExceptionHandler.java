package com.group1.gestao_seguranca.exception;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // Serve para os campos de validacao como (@NotBlank, @NotNull, @Size, etc.)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleValidacao(MethodArgumentNotValidException ex) {
        List<String> erros = ex.getBindingResult().getFieldErrors().stream().map(f -> f.getField() + ": " + f.getDefaultMessage()).toList();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiError(400, "Erro de validação", erros));
    }

    // Para os lançamentos de erro como EntityNotFound
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ApiError> handleNotFound(EntityNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiError(404, "Não encontrado", ex.getMessage()));
    }

    // Regra de negócio violada (saída já registada, chave indisponível, e outros.)
    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<ApiError> handleIllegalState(IllegalStateException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new ApiError(409, "Operação inválida", ex.getMessage()));
    }

    // Argumento inválido (funcionário e visitante em simultâneo)
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiError> handleIllegalArgument(IllegalArgumentException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiError(400, "Pedido inválido", ex.getMessage()));
    }

    // Quando o cliente não passar O VALOR de um parâmetro (como ?devolvidaPor=)
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ApiError> handleParametroEmFalta(MissingServletRequestParameterException ex) {
        String mensagem = "O parâmetro '" + ex.getParameterName() + "' é obrigatório.";
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ApiError(400, "Parâmetro em falta", mensagem));
    }

    // Obriga passar o valor dos parametros (como ?devolvidaPor=nome pessoa)
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiError> handleConstraintViolation(ConstraintViolationException ex) {
        List<String> erros = ex.getConstraintViolations()
                .stream()
                .map(v -> v.getPropertyPath() + ": " + v.getMessage())
                .toList();

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ApiError(400, "Erro de validação", erros));
    }

    // Qualquer outro erro não tratado — usar isso esconde o stack trace do cliente
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleGenerico(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiError(500, "Erro interno", "Ocorreu um erro inesperado."));
    }
}