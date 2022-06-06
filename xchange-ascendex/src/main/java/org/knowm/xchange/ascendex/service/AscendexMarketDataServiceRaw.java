package org.knowm.xchange.ascendex.service;

import java.io.IOException;
import java.util.List;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ascendex.AscendexException;
import org.knowm.xchange.ascendex.dto.marketdata.AscendexAssetDto;
import org.knowm.xchange.ascendex.dto.marketdata.AscendexBarHistDto;
import org.knowm.xchange.ascendex.dto.marketdata.AscendexMarketTradesDto;
import org.knowm.xchange.ascendex.dto.marketdata.AscendexOrderbookDto;
import org.knowm.xchange.ascendex.dto.marketdata.AscendexProductDto;

public class AscendexMarketDataServiceRaw extends AscendexBaseService {

  public AscendexMarketDataServiceRaw(Exchange exchange) {
    super(exchange);
  }

  public List<AscendexAssetDto> getAllAssets() throws AscendexException, IOException {
    return checkResult(ascendex.getAllAssets());
  }

  public List<AscendexProductDto> getAllProducts() throws AscendexException, IOException {
    return checkResult(ascendex.getAllProducts());
  }

  public AscendexOrderbookDto getAscendexOrderbook(String symbol)
      throws AscendexException, IOException {
    return checkResult(ascendex.getOrderbookDepth(symbol));
  }

  public AscendexMarketTradesDto getAscendexTrades(String symbol)
      throws AscendexException, IOException {
    return checkResult(ascendex.getTrades(symbol));
  }

  public List<AscendexBarHistDto> getBarHistoryData(
      String symbol, String interval, Long to, Long from, Integer noOfBars)
      throws AscendexException, IOException {
    return checkResult(ascendex.getHistoricalBarData(symbol, interval, to, from, noOfBars));
  }
}
