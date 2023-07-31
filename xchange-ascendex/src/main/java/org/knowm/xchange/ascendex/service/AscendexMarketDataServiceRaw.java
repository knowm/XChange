package org.knowm.xchange.ascendex.service;

import java.io.IOException;
import java.util.List;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ascendex.dto.enums.AccountCategory;
import org.knowm.xchange.ascendex.AscendexException;
import org.knowm.xchange.ascendex.dto.marketdata.*;

public class AscendexMarketDataServiceRaw extends AscendexBaseService {

  public AscendexMarketDataServiceRaw(Exchange exchange) {
    super(exchange);
  }

  @Deprecated
  public List<AscendexAssetDto> getAllAssets() throws AscendexException, IOException {
    return checkResult(ascendex.getAllAssets());
  }
  public List<AscendexAssetDto> getAllAssetsV2() throws AscendexException, IOException {
    return checkResult(ascendex.getAllAssetsV2());
  }

  public List<AscendexProductDto> getAllProducts() throws AscendexException, IOException {
    return checkResult(ascendex.getAllProducts());
  }

  public List<AscendexProductKindDto> getAllProducts(AccountCategory accountCategory) throws AscendexException, IOException {
    return checkResult(ascendex.getAllProducts(accountCategory));
  }


  public  AscendexTickerDto getTicker(String symbol) throws AscendexException, IOException {
    return checkResult(ascendex.getTicker(symbol.toUpperCase()));
  }



  public AscendexOrderbookDto getAscendexOrderbook(String symbol)
      throws AscendexException, IOException {
    return checkResult(ascendex.getOrderbookDepth(symbol.toUpperCase()));
  }

  public AscendexMarketTradesDto getAscendexTrades(String symbol)
      throws AscendexException, IOException {
    return getAscendexTrades(symbol.toUpperCase(),10);
  }

  /**
   *
   * @param symbol symbol
   * @param n any positive integer, capped at 100
   * @return
   * @throws AscendexException
   * @throws IOException
   */
  public AscendexMarketTradesDto getAscendexTrades(String symbol,Integer n)
          throws AscendexException, IOException {
    return checkResult(ascendex.getTrades(symbol.toUpperCase(),Math.min(n,100)));
  }

  public List<AscendexBarHistDto> getBarHistoryData(
      String symbol, String interval, Long to, Long from, Integer noOfBars)
      throws AscendexException, IOException {
    return checkResult(ascendex.getHistoricalBarData(symbol.toUpperCase(), interval, to, from, noOfBars));
  }
}
