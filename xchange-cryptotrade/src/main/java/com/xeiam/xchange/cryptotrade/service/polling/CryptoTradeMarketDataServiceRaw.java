package com.xeiam.xchange.cryptotrade.service.polling;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.cryptotrade.dto.CryptoTradeException;
import com.xeiam.xchange.cryptotrade.dto.CryptoTradePair;
import com.xeiam.xchange.cryptotrade.dto.CryptoTradePairs;
import com.xeiam.xchange.cryptotrade.dto.marketdata.CryptoTradeDepth;
import com.xeiam.xchange.cryptotrade.dto.marketdata.CryptoTradePublicTrade;
import com.xeiam.xchange.cryptotrade.dto.marketdata.CryptoTradePublicTrades;
import com.xeiam.xchange.cryptotrade.dto.marketdata.CryptoTradeTicker;
import com.xeiam.xchange.cryptotrade.dto.marketdata.CryptoTradeTickers;
import com.xeiam.xchange.currency.CurrencyPair;

public class CryptoTradeMarketDataServiceRaw extends CryptoTradeBasePollingService {

  /**
   * Constructor
   *
   * @param exchange
   */
  public CryptoTradeMarketDataServiceRaw(Exchange exchange) {

    super(exchange);
  }

  public CryptoTradeTicker getCryptoTradeTicker(CurrencyPair currencyPair) throws CryptoTradeException, IOException {

    CryptoTradeTicker cryptoTradeTicker = cryptoTradeAuthenticated.getTicker(currencyPair.baseSymbol.toLowerCase(), currencyPair.counterSymbol.toLowerCase());

    return handleResponse(cryptoTradeTicker);
  }

  public Map<CurrencyPair, CryptoTradeTicker> getCryptoTradeTickers() throws CryptoTradeException, IOException {

    CryptoTradeTickers cryptoTradeTickers = cryptoTradeAuthenticated.getTickers();

    return handleResponse(cryptoTradeTickers).getTickers();
  }

  public CryptoTradeDepth getCryptoTradeOrderBook(CurrencyPair currencyPair) throws CryptoTradeException, IOException {

    CryptoTradeDepth cryptoTradeDepth = cryptoTradeAuthenticated
        .getFullDepth(currencyPair.baseSymbol.toLowerCase(), currencyPair.counterSymbol.toLowerCase());

    return handleResponse(cryptoTradeDepth);
  }

  public List<CryptoTradePublicTrade> getCryptoTradeTradeHistory(CurrencyPair currencyPair) throws CryptoTradeException, IOException {

    CryptoTradePublicTrades cryptoTradeDepth = cryptoTradeAuthenticated.getTradeHistory(currencyPair.baseSymbol.toLowerCase(),
        currencyPair.counterSymbol.toLowerCase());

    return handleResponse(cryptoTradeDepth).getPublicTrades();
  }

  public List<CryptoTradePublicTrade> getCryptoTradeTradeHistory(CurrencyPair currencyPair, long sinceTimestamp) throws CryptoTradeException,
      IOException {

    CryptoTradePublicTrades cryptoTradeDepth = cryptoTradeAuthenticated.getTradeHistory(currencyPair.baseSymbol.toLowerCase(),
        currencyPair.counterSymbol.toLowerCase(), sinceTimestamp);

    return handleResponse(cryptoTradeDepth).getPublicTrades();
  }

  public CryptoTradePair getCryptoTradePairInfo(CurrencyPair currencyPair) throws CryptoTradeException, IOException {

    CryptoTradePair cryptoTradePair = cryptoTradeAuthenticated.getPair(currencyPair.baseSymbol.toLowerCase(), currencyPair.counterSymbol.toLowerCase());

    return handleResponse(cryptoTradePair);
  }

  public Map<CurrencyPair, CryptoTradePair> getCryptoTradePairs() throws CryptoTradeException, IOException {

    CryptoTradePairs cryptoPairs = cryptoTradeAuthenticated.getPairs();

    return handleResponse(cryptoPairs).getPairs();
  }
}
