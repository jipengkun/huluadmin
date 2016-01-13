package com.hulu.diagnoseTime.exception;

/**
 * Created by huangyiwei on 15/11/3.
 */
public class CannotUpdateDiagnoseNumBalace extends RuntimeException {

    public CannotUpdateDiagnoseNumBalace() {
        super();
    }

    public CannotUpdateDiagnoseNumBalace(String message) {
        super(message);
    }

    public CannotUpdateDiagnoseNumBalace(String message, Throwable cause) {
        super(message, cause);
    }
}
