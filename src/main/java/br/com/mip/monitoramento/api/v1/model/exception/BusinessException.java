package br.com.mip.monitoramento.api.v1.model.exception;

public class BusinessException extends RuntimeException {

    public BusinessException(String message) {
        super(message);
    }
}
