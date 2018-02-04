package org.knowm.xchange.bitmex.service;

import org.knowm.xchange.Exchange;

import java.io.IOException;
import java.util.List;

public class BitmexTradeServiceRaw extends BitmexBaseService {

    public static final PositionApi POSITION_API = new PositionApi();
    /**
     * Constructor
     *
     * @param exchange
     */
    String apiKey = null;

    public BitmexTradeServiceRaw(Exchange exchange) {

        super(exchange);
    }

    public List<org.knowm.xchange.bitmex.dto.BitmexPosition> getBitmexPositions() throws IOException {

        try {
            return POSITION_API.positionGet(null, null, null);
        } catch (Exception e) {
            throw handleError(e);
        }
    }

    public List<org.knowm.xchange.bitmex.dto.BitmexPosition> getBitmexPositions(String symbol) throws IOException {

        try {
            return POSITION_API.positionGet(symbol, null, null);
        } catch (Exception e) {
            throw handleError(e);
        }
    }
}
