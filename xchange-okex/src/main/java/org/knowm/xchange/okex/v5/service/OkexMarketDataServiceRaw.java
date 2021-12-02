package org.knowm.xchange.okex.v5.service;

import com.fasterxml.jackson.databind.JsonNode;

import org.knowm.xchange.client.ResilienceRegistries;
import org.knowm.xchange.okex.v5.OkexExchange;
import org.knowm.xchange.okex.v5.dto.OkexException;
import org.knowm.xchange.okex.v5.dto.OkexResponse;
import org.knowm.xchange.okex.v5.dto.marketdata.*;
import org.knowm.xchange.utils.DateUtils;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import static org.knowm.xchange.okex.v5.Okex.instrumentsPath;
import static org.knowm.xchange.okex.v5.OkexAuthenticated.currenciesPath;

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
          .withRateLimiter(rateLimiter(instrumentsPath))
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
          .withRateLimiter(rateLimiter(currenciesPath))
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

  public OkexResponse<List<OkexOrderbook>> getOkexOrderbook(String instrument, int depth)
      throws OkexException, IOException {
    OkexResponse<List<OkexOrderbook>> books =
        okex.getOrderbook(
            instrument,
            depth,
            (String)
                exchange.getExchangeSpecification().getExchangeSpecificParametersItem("simulated"));
    return books;
  }

  public OkexResponse<List<OkexCandleStick>> getHistoryCandle(
          String instrument,
          String after,
          String before,
          String bar,
          String limit)
          throws OkexException, IOException {
    return okex.getHistoryCandles(instrument, after, before, bar, limit, (String) exchange.getExchangeSpecification().getExchangeSpecificParametersItem("simulated"));
  }
}
