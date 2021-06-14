package org.knowm.xchange.bitmax.service;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.bitmax.BitmaxException;
import org.knowm.xchange.bitmax.dto.marketdata.BitmaxAssetDto;
import org.knowm.xchange.bitmax.dto.marketdata.BitmaxMarketTradesDto;
import org.knowm.xchange.bitmax.dto.marketdata.BitmaxOrderbookDto;
import org.knowm.xchange.bitmax.dto.marketdata.BitmaxProductDto;

import java.io.IOException;
import java.util.List;

public class BitmaxMarketDataServiceRaw extends BitmaxBaseService {

  public BitmaxMarketDataServiceRaw(Exchange exchange) {
    super(exchange);
  }

  public List<BitmaxAssetDto> getAllAssets() throws BitmaxException, IOException {
    try {
      return checkResult(bitmax.getAllAssets());
    } catch (BitmaxException e) {
      throw new BitmaxException(e.getMessage());
    }
  }

  public List<BitmaxProductDto> getAllProducts() throws BitmaxException, IOException {
    try {
      return checkResult(bitmax.getAllProducts());
    } catch (BitmaxException e) {
      throw new BitmaxException(e.getMessage());
    }
  }

  public BitmaxOrderbookDto getBitmaxOrderbook(String symbol) throws BitmaxException, IOException {
    try {
      return checkResult(bitmax.getOrderbookDepth(symbol));
    } catch (BitmaxException e) {
      throw new BitmaxException(e.getMessage());
    }
  }

  public BitmaxMarketTradesDto getBitmaxTrades(String symbol) throws BitmaxException, IOException {
    try {
      return checkResult(bitmax.getTrades(symbol));
    } catch (BitmaxException e) {
      throw new BitmaxException(e.getMessage());
    }
  }
}
