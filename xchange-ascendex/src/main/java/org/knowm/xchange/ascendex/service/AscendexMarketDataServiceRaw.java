package org.knowm.xchange.ascendex.service;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.ascendex.AscendexException;
import org.knowm.xchange.ascendex.dto.marketdata.AscendexAssetDto;
import org.knowm.xchange.ascendex.dto.marketdata.AscendexMarketTradesDto;
import org.knowm.xchange.ascendex.dto.marketdata.AscendexOrderbookDto;
import org.knowm.xchange.ascendex.dto.marketdata.AscendexProductDto;

import java.io.IOException;
import java.util.List;

public class AscendexMarketDataServiceRaw extends AscendexBaseService {

  public AscendexMarketDataServiceRaw(Exchange exchange) {
    super(exchange);
  }

  public List<AscendexAssetDto> getAllAssets() throws AscendexException, IOException {
    return checkResult(bitmax.getAllAssets());
  }

  public List<AscendexProductDto> getAllProducts() throws AscendexException, IOException {
    return checkResult(bitmax.getAllProducts());
  }

  public AscendexOrderbookDto getBitmaxOrderbook(String symbol) throws AscendexException, IOException {
    return checkResult(bitmax.getOrderbookDepth(symbol));
  }

  public AscendexMarketTradesDto getBitmaxTrades(String symbol) throws AscendexException, IOException {
    return checkResult(bitmax.getTrades(symbol));
  }
}
