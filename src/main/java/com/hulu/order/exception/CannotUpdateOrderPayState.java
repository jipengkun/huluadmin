package com.hulu.order.exception;

/**
 * Created by huangyiwei on 15/11/3.
 */
public class CannotUpdateOrderPayState extends RuntimeException {

    public CannotUpdateOrderPayState() {
        super();
    }

    public CannotUpdateOrderPayState(String message) {
        super(message);
    }

    public CannotUpdateOrderPayState(String message, Throwable cause) {
        super(message, cause);
    }
}
