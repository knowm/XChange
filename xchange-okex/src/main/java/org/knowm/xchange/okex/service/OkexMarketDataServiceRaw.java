package org.knowm.xchange.okex.service;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.knowm.xchange.client.ResilienceRegistries;
import org.knowm.xchange.okex.Okex;
import org.knowm.xchange.okex.OkexAuthenticated;
import org.knowm.xchange.okex.OkexExchange;
import org.knowm.xchange.okex.dto.OkexException;
import org.knowm.xchange.okex.dto.OkexResponse;
import org.knowm.xchange.okex.dto.marketdata.OkexCandleStick;
import org.knowm.xchange.okex.dto.marketdata.OkexCurrency;
import org.knowm.xchange.okex.dto.marketdata.OkexInstrument;
import org.knowm.xchange.okex.dto.marketdata.OkexOrderbook;
import org.knowm.xchange.okex.dto.marketdata.OkexTicker;
import org.knowm.xchange.okex.dto.marketdata.OkexTrade;
import org.knowm.xchange.utils.DateUtils;

/** Author: Max Gao (gaamox@tutanota.com) Created: 08-06-2021 */
public class OkexMarketDataServiceRaw extends OkexBaseService {
  public OkexMarketDataServiceRaw(
      OkexExchange exchange, ResilienceRegistries resilienceRegistries) {
    super(exchange, resilienceRegistries);
  }

  public OkexResponse<List<OkexInstrument>> getOkexInstruments(
      String instrumentType, String underlying, String instrumentId)
      throws OkexException, IOException {
    try {
      return decorateApiCall(
              () ->
                  okex.getInstruments(
                      instrumentType,
                      underlying,
                      instrumentId,
                      (String)
                          exchange
                              .getExchangeSpecification()
                              .getExchangeSpecificParametersItem("simulated")))
          .withRateLimiter(rateLimiter(Okex.instrumentsPath))
          .call();
    } catch (OkexException e) {
      throw handleError(e);
    }
  }

  public OkexResponse<List<OkexCurrency>> getOkexCurrencies() throws OkexException, IOException {
    try {
      return decorateApiCall(
              () ->
                  okexAuthenticated.getCurrencies(
                      exchange.getExchangeSpecification().getApiKey(),
                      signatureCreator,
                      DateUtils.toUTCISODateString(new Date()),
                      (String)
                          exchange
                              .getExchangeSpecification()
                              .getExchangeSpecificParametersItem("passphrase"),
                      (String)
                          exchange
                              .getExchangeSpecification()
                              .getExchangeSpecificParametersItem("simulated")))
          .withRateLimiter(rateLimiter(OkexAuthenticated.currenciesPath))
          .call();
    } catch (OkexException e) {
      throw handleError(e);
    }
  }

  public OkexResponse<List<OkexTrade>> getOkexTrades(String instrument, int limit)
      throws OkexException, IOException {

    return okex.getTrades(
        instrument,
        limit,
        (String)
            exchange.getExchangeSpecification().getExchangeSpecificParametersItem("simulated"));
  }

  public OkexResponse<List<OkexOrderbook>> getOkexOrderbook(String instrument)
      throws OkexException, IOException {
    OkexResponse<List<OkexOrderbook>> books =
        okex.getOrderbook(
            instrument,
            20,
            (String)
                exchange.getExchangeSpecification().getExchangeSpecificParametersItem("simulated"));
    return books;
  }

  public OkexResponse<List<OkexCandleStick>> getHistoryCandle(
      String instrument, String after, String before, String bar, String limit)
      throws OkexException, IOException {
    return okex.getHistoryCandles(
        instrument,
        after,
        before,
        bar,
        limit,
        (String)
            exchange.getExchangeSpecification().getExchangeSpecificParametersItem("simulated"));
  }

  public OkexResponse<List<OkexTicker>> getTickers(String instType, String uly, String instFamily)
      throws OkexException, IOException {
    return okex.getTickers(instType, uly, instFamily);
  }

  public OkexResponse<List<OkexTicker>> getTicker(String instrument)
      throws OkexException, IOException {
    return okex.getTicker(instrument);
  }
}
