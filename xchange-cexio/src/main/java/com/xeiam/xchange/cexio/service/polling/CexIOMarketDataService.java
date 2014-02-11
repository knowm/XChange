package com.xeiam.xchange.cexio.service.polling;

import java.io.IOException;
import java.util.List;

import si.mazi.rescu.RestProxyFactory;

import com.xeiam.xchange.ExchangeException;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.NotAvailableFromExchangeException;
import com.xeiam.xchange.NotYetImplementedForExchangeException;
import com.xeiam.xchange.cexio.CexIO;
import com.xeiam.xchange.cexio.CexIOAdapters;
import com.xeiam.xchange.cexio.CexIOUtils;
import com.xeiam.xchange.cexio.dto.marketdata.CexIODepth;
import com.xeiam.xchange.cexio.dto.marketdata.CexIOTicker;
import com.xeiam.xchange.cexio.dto.marketdata.CexIOTrade;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.ExchangeInfo;
import com.xeiam.xchange.dto.marketdata.OrderBook;
import com.xeiam.xchange.dto.marketdata.Ticker;
import com.xeiam.xchange.dto.marketdata.Trades;
import com.xeiam.xchange.service.polling.BasePollingExchangeService;
import com.xeiam.xchange.service.polling.PollingMarketDataService;
import com.xeiam.xchange.utils.Assert;

/**
 * Author: brox
 * Since: 2/6/14
 */

public class CexIOMarketDataService extends BasePollingExchangeService implements PollingMarketDataService {

  private final CexIO exchange;

  /**
   * Initialize common properties from the exchange specification
   * 
   * @param exchangeSpecification The {@link com.xeiam.xchange.ExchangeSpecification}
   */
  public CexIOMarketDataService(ExchangeSpecification exchangeSpecification) {

    super(exchangeSpecification);
    this.exchange = RestProxyFactory.createProxy(CexIO.class, exchangeSpecification.getSslUri());
  }

  @Override
  public List<CurrencyPair> getExchangeSymbols() {

    return null;
  }

  @Override
  public Ticker getTicker(String tradableIdentifier, String currency, Object... args) throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {

    verify(tradableIdentifier, currency);
    CexIOTicker bitstampTicker = exchange.getTicker(tradableIdentifier, currency);

    return CexIOAdapters.adaptTicker(bitstampTicker, tradableIdentifier, currency);
  }

  @Override
  public OrderBook getOrderBook(String tradableIdentifier, String currency, Object... args) throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException,
      IOException {

    verify(tradableIdentifier, currency);
    CexIODepth bitstampOrderBook = exchange.getDepth(tradableIdentifier, currency);

    return CexIOAdapters.adaptOrderBook(bitstampOrderBook, tradableIdentifier, currency);
  }

  @Override
  public Trades getTrades(String tradableIdentifier, String currency, Object... args) throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {

    verify(tradableIdentifier, currency);
    CexIOTrade[] trades;

    if (args.length > 0) {
      Object arg = args[0];
      if (!(arg instanceof Integer) || ((Integer) arg < 1)) {
        throw new ExchangeException("Size argument must be an Integer > 1");
      }
      else {
        trades = exchange.getTrades(tradableIdentifier, currency, (Integer) arg);
      }
    }
    else { // default to full available trade history
      trades = exchange.getTrades(tradableIdentifier, currency);
    }

    return CexIOAdapters.adaptTrades(trades, tradableIdentifier, currency);
  }

  @Override
  public ExchangeInfo getExchangeInfo() throws ExchangeException, IOException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException {

    throw new NotAvailableFromExchangeException();
  }

  /**
   * Verify that both currencies can make valid pair
   * 
   * @param tradableIdentifier The tradeable identifier (e.g. BTC in BTC/USD)
   * @param currency
   */
  private void verify(String tradableIdentifier, String currency) throws IOException {

    Assert.notNull(tradableIdentifier, "tradableIdentifier cannot be null");
    Assert.notNull(currency, "currency cannot be null");
    Assert.isTrue(CexIOUtils.isValidCurrencyPair(new CurrencyPair(tradableIdentifier, currency)), "currencyPair is not valid:" + tradableIdentifier + " " + currency);
  }

}
