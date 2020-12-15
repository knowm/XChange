package org.knowm.xchange.ftx.service;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.ftx.FtxException;
import org.knowm.xchange.ftx.dto.marketdata.FtxMarketsResponse;
import org.knowm.xchange.ftx.dto.marketdata.FtxOrderbookResponse;

import java.io.IOException;

public class FtxMarketDataServiceRaw extends FtxBaseService{

    public FtxMarketDataServiceRaw(Exchange exchange) {
        super(exchange);
    }

    public FtxMarketsResponse getFtxMarkets() throws FtxException, IOException {
        return ftx.getMarkets();
    }

    public FtxOrderbookResponse getFtxOrderbook(String market) throws FtxException, IOException{
        return ftx.getOrderbook(market,20);
    }
}
