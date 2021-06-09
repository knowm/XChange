package org.knowm.xchange.okex.v5.service;

import org.knowm.xchange.client.ResilienceRegistries;
import org.knowm.xchange.okex.v5.OkexExchange;
import org.knowm.xchange.okex.v5.dto.OkexException;
import org.knowm.xchange.okex.v5.dto.OkexResponse;
import org.knowm.xchange.okex.v5.dto.marketdata.OkexCurrency;
import org.knowm.xchange.okex.v5.dto.marketdata.OkexInstrument;
import org.knowm.xchange.utils.DateUtils;

import java.io.IOException;
import java.util.Date;
import java.util.List;

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
      return okex.getInstruments(instrumentType, underlying, instrumentId);
    } catch (OkexException e) {
      throw new OkexException(e.getMessage());
    }
  }

  public OkexResponse<List<OkexCurrency>> getOkexCurrencies() throws OkexException, IOException {
    try {
      return okexAuthenticated.getCurrencies(
          exchange.getExchangeSpecification().getApiKey(),
          signatureCreator,
          DateUtils.toUTCISODateString(new Date()),
          (String)
              exchange.getExchangeSpecification().getExchangeSpecificParametersItem("passphrase"));
    } catch (OkexException e) {
      throw new OkexException(e.getMessage());
    }
  }
}
