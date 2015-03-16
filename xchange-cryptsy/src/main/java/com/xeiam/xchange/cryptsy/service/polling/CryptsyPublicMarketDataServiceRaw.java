package com.xeiam.xchange.cryptsy.service.polling;

import java.io.IOException;
import java.util.Map;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.cryptsy.CryptsyAdapters;
import com.xeiam.xchange.cryptsy.dto.marketdata.CryptsyCurrencyPairsReturn;
import com.xeiam.xchange.cryptsy.dto.marketdata.CryptsyPublicMarketData;
import com.xeiam.xchange.cryptsy.dto.marketdata.CryptsyPublicOrderbook;
import com.xeiam.xchange.exceptions.ExchangeException;

public class CryptsyPublicMarketDataServiceRaw extends CryptsyBasePollingService {

  /**
   * Constructor
   *
   * @param exchange
   */
  public CryptsyPublicMarketDataServiceRaw(Exchange exchange) {

    super(exchange);
  }

  public Map<Integer, CryptsyPublicMarketData> getAllCryptsyMarketData() throws IOException, ExchangeException {

    return CryptsyAdapters.adaptPublicMarketDataMap(checkResult(cryptsy.getAllMarketData()).getReturnValue());
  }

  public Map<Integer, CryptsyPublicMarketData> getCryptsyMarketData(int marketId) throws IOException, ExchangeException {

    return CryptsyAdapters.adaptPublicMarketDataMap(checkResult(cryptsy.getMarketData(marketId)).getReturnValue());
  }

  public Map<Integer, CryptsyPublicOrderbook> getAllCryptsyOrderBooks() throws IOException, ExchangeException {

    return CryptsyAdapters.adaptPublicOrderBookMap(checkResult(cryptsy.getAllOrderbookData()).getReturnValue());
  }

  public Map<Integer, CryptsyPublicOrderbook> getCryptsyOrderBook(int marketId) throws IOException, ExchangeException {

    return CryptsyAdapters.adaptPublicOrderBookMap(checkResult(cryptsy.getOrderbookData(marketId)).getReturnValue());
  }

  public CryptsyCurrencyPairsReturn getCryptsyCurrencyPairs() throws IOException {

    return checkResult(cryptsy.getCryptsyCurrencyPairs());
  }
}
