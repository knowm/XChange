package org.known.xchange.dsx.dto.marketdata;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonCreator;

/**
 * @author Mikhail Wall
 */

public class DSXOrderbookWrapper {

    private final Map<String, DSXOrderbook> orderbookMap;


    @JsonCreator
    public DSXOrderbookWrapper(Map<String, DSXOrderbook> result) {

        this.orderbookMap = result;
    }

    public DSXOrderbook getOrderbook(String pair) {
        DSXOrderbook result = null;
        if (orderbookMap.containsKey(pair)) {
            result = orderbookMap.get(pair);
        }
        return result;
    }

    @Override
    public String toString() {
        return "DSXOrderbookWrapper{" +
                "orderbookMap=" + orderbookMap +
                '}';
    }

}
