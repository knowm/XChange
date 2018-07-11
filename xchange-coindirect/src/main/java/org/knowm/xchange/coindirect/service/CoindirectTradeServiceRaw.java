package org.knowm.xchange.coindirect.service;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.coindirect.dto.CoindirectException;
import org.knowm.xchange.coindirect.dto.trade.CoindirectOrder;

import java.io.IOException;
import java.util.List;

public class CoindirectTradeServiceRaw extends CoindirectBaseService {
    /**
     * Constructor
     *
     * @param exchange
     */
    protected CoindirectTradeServiceRaw(Exchange exchange) {
        super(exchange);
    }

    List<CoindirectOrder> listExchangeOrders(String symbol, boolean completed, long offset, long max) throws IOException, CoindirectException {
        return coindirect.listExchangeOrders(symbol, completed, offset, max, signatureCreator);
    }
}
