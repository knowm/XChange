package org.knowm.xchange.ftx.service;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.service.marketdata.MarketDataService;

public class FtxMarketDataService extends FtxMarketDataServiceRaw implements MarketDataService {

    public FtxMarketDataService(Exchange exchange) {
        super(exchange);
    }
}
