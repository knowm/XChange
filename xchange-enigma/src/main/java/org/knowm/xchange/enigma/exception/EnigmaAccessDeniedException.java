package org.knowm.xchange.enigma.exception;

public class EnigmaAccessDeniedException extends RuntimeException {

    public EnigmaAccessDeniedException() {
        super("Access denied to resource");
    }

}