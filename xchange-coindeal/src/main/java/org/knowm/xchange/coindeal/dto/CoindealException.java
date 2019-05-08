package org.knowm.xchange.coindeal.dto;

import si.mazi.rescu.HttpStatusIOException;
import si.mazi.rescu.InvocationResult;

public class CoindealException extends HttpStatusIOException {

    public CoindealException(String message, InvocationResult invocationResult) {
        super(message, invocationResult);
    }
}
