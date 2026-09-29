package org.knowm.xchange.cryptocom.service;

import java.io.IOException;
import java.util.List;
import org.knowm.xchange.client.ResilienceRegistries;
import org.knowm.xchange.cryptocom.CryptoComExchange;
import org.knowm.xchange.cryptocom.dto.CryptoComException;
import org.knowm.xchange.cryptocom.dto.CryptoComResponse;
import org.knowm.xchange.cryptocom.dto.marketdata.CryptoComInstrument;
import org.knowm.xchange.cryptocom.dto.marketdata.CryptoComOrderBookData;
import org.knowm.xchange.cryptocom.dto.marketdata.CryptoComPublicTrade;
import org.knowm.xchange.cryptocom.dto.marketdata.CryptoComTicker;

public class CryptoComMarketDataServiceRaw extends CryptoComBaseService {

  protected CryptoComMarketDataServiceRaw(
      CryptoComExchange exchange, ResilienceRegistries resilienceRegistries) {
    super(exchange, resilienceRegistries);
  }

  public List<CryptoComInstrument> getCryptoComInstruments()
      throws IOException, CryptoComException {
    CryptoComResponse response = decorateApiCall(cryptoCom::getInstruments).call();
    return getDataList(response, CryptoComInstrument.class);
  }

  public CryptoComTicker getCryptoComTicker(String instrumentName)
      throws IOException, CryptoComException {
    CryptoComResponse response = decorateApiCall(() -> cryptoCom.getTickers(instrumentName)).call();
    List<CryptoComTicker> tickers = getDataList(response, CryptoComTicker.class);
    return tickers.isEmpty() ? null : tickers.get(0);
  }

  public List<CryptoComTicker> getCryptoComTickers() throws IOException, CryptoComException {
    CryptoComResponse response = decorateApiCall(() -> cryptoCom.getTickers(null)).call();
    return getDataList(response, CryptoComTicker.class);
  }

  public CryptoComOrderBookData getCryptoComOrderBook(String instrumentName, Integer depth)
      throws IOException, CryptoComException {
    CryptoComResponse response =
        decorateApiCall(() -> cryptoCom.getBook(instrumentName, depth)).call();
    List<CryptoComOrderBookData> data = getDataList(response, CryptoComOrderBookData.class);
    return data.isEmpty() ? null : data.get(0);
  }

  public List<CryptoComPublicTrade> getCryptoComTrades(String instrumentName, Integer count)
      throws IOException, CryptoComException {
    CryptoComResponse response =
        decorateApiCall(() -> cryptoCom.getPublicTrades(instrumentName, count)).call();
    return getDataList(response, CryptoComPublicTrade.class);
  }
}
