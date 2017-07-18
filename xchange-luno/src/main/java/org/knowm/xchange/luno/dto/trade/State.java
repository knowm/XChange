package org.knowm.xchange.luno.dto.trade;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum State {
    PENDING, COMPLETE, UNKNOWN;
    
    @JsonCreator
    public static State create(String s) {
        try {
            return State.valueOf(s);
        } catch (Exception e) {
            return UNKNOWN;
        }
    }
}