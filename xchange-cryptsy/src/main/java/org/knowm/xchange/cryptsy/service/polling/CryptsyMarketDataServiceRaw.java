package org.knowm.xchange.cryptsy.service.polling;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.cryptsy.CryptsyAdapters;
import org.knowm.xchange.cryptsy.CryptsyCurrencyUtils;
import org.knowm.xchange.cryptsy.dto.marketdata.CryptsyCurrencyPairsReturn;
import org.knowm.xchange.cryptsy.dto.marketdata.CryptsyGetMarketsReturn;
import org.knowm.xchange.cryptsy.dto.marketdata.CryptsyMarketId;
import org.knowm.xchange.cryptsy.dto.marketdata.CryptsyMarketTradesReturn;
import org.knowm.xchange.cryptsy.dto.marketdata.CryptsyOrderBookReturn;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.exceptions.ExchangeException;

/**
 * @author ObsessiveOrange
 */
public class CryptsyMarketDataServiceRaw extends CryptsyBasePollingService {

  /**
   * Constructor
   *
   * @param exchange
   */
  public CryptsyMarketDataServiceRaw(Exchange exchange) {

    super(exchange);
  }

  /**
   * Get the orderbook for this market
   *
   * @param marketID the marketID to get the orderbook for
   * @return CryptsyOrderBookReturn DTO representing the overall orderbook
   * @throws ExchangeException Indication that the exchange reported some kind of error with the request or response. Implementers should log this
   *         error.
   * @throws IOException
   */
  public CryptsyOrderBookReturn getCryptsyOrderBook(int marketID) throws IOException, ExchangeException {

    return checkResult(cryptsyAuthenticated.marketorders(apiKey, signatureCreator, exchange.getNonceFactory(), marketID));
  }

  /**
   * @param marketID the marketID to get the orderbook for
   * @return CryptsyMarketTradesReturn DTO representing the past trades in this market
   * @throws ExchangeException Indication that the exchange reported some kind of error with the request or response. Implementers should log this
   *         error.
   * @throws IOException
   */
  public CryptsyMarketTradesReturn getCryptsyTrades(int marketID) throws IOException, ExchangeException {

    return checkResult(cryptsyAuthenticated.markettrades(apiKey, signatureCreator, exchange.getNonceFactory(), marketID));
  }

  /**
   * Get all active markets from exchange
   *
   * @return CryptsyTradesWrapper DTO representing market summary information
   * @throws ExchangeException Indication that the exchange reported some kind of error with the request or response. Implementers should log this
   *         error.
   * @throws IOException
   */
  public CryptsyGetMarketsReturn getCryptsyMarkets() throws IOException, ExchangeException {

    return checkResult(cryptsyAuthenticated.getmarkets(apiKey, signatureCreator, exchange.getNonceFactory()));
  }

  public List<CurrencyPair> getExchangeSymbols() throws IOException {

    List<CurrencyPair> currencyPairs = new ArrayList<CurrencyPair>();

    CryptsyCurrencyPairsReturn response = cryptsy.getCryptsyCurrencyPairs();
    HashMap<String, CryptsyMarketId> map = response.getReturnValue();

    CryptsyCurrencyUtils.marketIds_CurrencyPairs.clear();
    CryptsyCurrencyUtils.currencyPairs_MarketIds.clear();

    for (String pairString : map.keySet()) {
      CurrencyPair currencyPair = CryptsyAdapters.adaptCurrencyPair(pairString);
      String idString = map.get(pairString).getMarketid();
      Integer marketId = Integer.valueOf(idString);

      CryptsyCurrencyUtils.marketIds_CurrencyPairs.put(marketId, currencyPair);
      CryptsyCurrencyUtils.currencyPairs_MarketIds.put(currencyPair, marketId);
      currencyPairs.add(currencyPair);
    }

    return currencyPairs;
  }
}
