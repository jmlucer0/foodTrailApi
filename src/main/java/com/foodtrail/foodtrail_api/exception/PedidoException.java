package com.foodtrail.foodtrail_api.exception;

public class PedidoException extends RuntimeException{
    public PedidoException() {
        super();
    }

    public PedidoException(String message) {
        super(message);
    }

    public PedidoException(String message, Throwable cause) {
        super(message, cause);
    }

    public PedidoException(Throwable cause) {
        super(cause);
    }
}
