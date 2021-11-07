package org.knowm.xchange.okex.v5.service;

import static org.knowm.xchange.okex.v5.Okex.instrumentsPath;
import static org.knowm.xchange.okex.v5.OkexAuthenticated.currenciesPath;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import org.knowm.xchange.client.ResilienceRegistries;
import org.knowm.xchange.okex.v5.OkexExchange;
import org.knowm.xchange.okex.v5.dto.OkexException;
import org.knowm.xchange.okex.v5.dto.OkexResponse;
import org.knowm.xchange.okex.v5.dto.marketdata.OkexCurrency;
import org.knowm.xchange.okex.v5.dto.marketdata.OkexInstrument;
import org.knowm.xchange.okex.v5.dto.marketdata.OkexOrderbook;
import org.knowm.xchange.okex.v5.dto.marketdata.OkexTrade;
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
}
