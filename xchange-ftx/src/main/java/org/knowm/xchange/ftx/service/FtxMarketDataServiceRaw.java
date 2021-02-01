package org.knowm.xchange.ftx.service;

import java.io.IOException;
import java.util.List;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.ftx.FtxException;
import org.knowm.xchange.ftx.dto.FtxResponse;
import org.knowm.xchange.ftx.dto.marketdata.FtxMarketsDto;
import org.knowm.xchange.ftx.dto.marketdata.FtxOrderbookDto;
import org.knowm.xchange.ftx.dto.marketdata.FtxTradeDto;

public class FtxMarketDataServiceRaw extends FtxBaseService {

  public FtxMarketDataServiceRaw(Exchange exchange) {
    super(exchange);
  }

  public FtxResponse<FtxMarketsDto> getFtxMarkets() throws FtxException, IOException {
    try {
      return ftx.getMarkets();
    } catch (FtxException e) {
      throw new FtxException(e.getMessage());
    }
  }

  public FtxResponse<List<FtxTradeDto>> getFtxTrades(String market)
          throws FtxException, IOException {
    try {
      return ftx.getTrades(market, 30);
    } catch (FtxException e) {
      throw new FtxException(e.getMessage());
    }
  }

  public FtxResponse<FtxOrderbookDto> getFtxOrderbook(String market)
      throws FtxException, IOException {
    try {
      return ftx.getOrderbook(market, 20);
    } catch (FtxException e) {
      throw new FtxException(e.getMessage());
    }
  }
}
