package org.xchange.kraken.service.polling;

import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.xchange.kraken.Kraken;
import org.xchange.kraken.KrakenAdapters;
import org.xchange.kraken.KrakenUtils;
import org.xchange.kraken.dto.marketdata.KrakenAssetPairsResult;
import org.xchange.kraken.dto.marketdata.KrakenDepth;
import org.xchange.kraken.dto.marketdata.KrakenDepthResult;
import org.xchange.kraken.dto.marketdata.KrakenTicker;
import org.xchange.kraken.dto.marketdata.KrakenTickerResult;
import org.xchange.kraken.dto.marketdata.KrakenTradesResult;

import si.mazi.rescu.RestProxyFactory;

import com.xeiam.xchange.ExchangeException;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.NotAvailableFromExchangeException;
import com.xeiam.xchange.NotYetImplementedForExchangeException;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.marketdata.OrderBook;
import com.xeiam.xchange.dto.marketdata.Ticker;
import com.xeiam.xchange.dto.marketdata.Trades;
import com.xeiam.xchange.dto.trade.LimitOrder;
import com.xeiam.xchange.service.polling.BasePollingExchangeService;
import com.xeiam.xchange.service.polling.PollingMarketDataService;
import com.xeiam.xchange.utils.Assert;

public class KrakenPollingMarketDataService extends BasePollingExchangeService implements PollingMarketDataService {

  private static final long PARTIAL_ORDERBOOK_SIZE = 200L;
  private final Kraken kraken;

  public KrakenPollingMarketDataService(ExchangeSpecification exchangeSpecification) {

    super(exchangeSpecification);
    Assert.notNull(exchangeSpecification.getSslUri(), "Exchange specification URI cannot be null");
    kraken = RestProxyFactory.createProxy(Kraken.class, exchangeSpecification.getSslUri());
  }

  @Override
  public List<CurrencyPair> getExchangeSymbols() {

    KrakenAssetPairsResult krakenAssetPairs = kraken.getAssetPairs();
    if (krakenAssetPairs.getError().length > 0) {
      throw new ExchangeException(krakenAssetPairs.getError().toString());
    }
    KrakenAdapters.adaptCurrencyPairs(krakenAssetPairs.getResult().keySet());
    throw new NotYetImplementedForExchangeException();
  }

  @Override
  public Ticker getTicker(String tradableIdentifier, String currency) throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException {

    String krakenCurrencyPair = KrakenUtils.createKrakenCurrencyPair(tradableIdentifier, currency);
    KrakenTickerResult krakenTickerResult = kraken.getTicker(krakenCurrencyPair);
    if (krakenTickerResult.getError().length > 0) {
      throw new ExchangeException(krakenTickerResult.getError().toString());
    }
    KrakenTicker krakenTicker = krakenTickerResult.getResult().get(krakenCurrencyPair);

    return KrakenAdapters.adaptTicker(krakenTicker, currency, tradableIdentifier);
  }

  @Override
  public OrderBook getPartialOrderBook(String tradableIdentifier, String currency) throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException {

    return getOrderBook(tradableIdentifier, currency, PARTIAL_ORDERBOOK_SIZE);

  }

  @Override
  public OrderBook getFullOrderBook(String tradableIdentifier, String currency) throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException {

    return getOrderBook(tradableIdentifier, currency, null);

  }

  private OrderBook getOrderBook(String tradableIdentifier, String currency, Long count) {

    String krakenCurrencyPair = KrakenUtils.createKrakenCurrencyPair(tradableIdentifier, currency);
    KrakenDepthResult krakenDepthReturn = kraken.getDepth(krakenCurrencyPair, count);
    if (krakenDepthReturn.getError().length > 0) {
      throw new ExchangeException(krakenDepthReturn.getError().toString());
    }
    KrakenDepth krakenDepth = krakenDepthReturn.getResult().get(krakenCurrencyPair);
    List<LimitOrder> bids = KrakenAdapters.adaptOrders(krakenDepth.getBids(), currency, tradableIdentifier, "bids");
    List<LimitOrder> asks = KrakenAdapters.adaptOrders(krakenDepth.getAsks(), currency, tradableIdentifier, "asks");
    Comparator<LimitOrder> dateComparator = new Comparator<LimitOrder>() {

      @Override
      public int compare(LimitOrder o1, LimitOrder o2) {

        return o1.getTimestamp().compareTo(o2.getTimestamp());
      }
    };
    bids.addAll(asks);
    Date timeStamp = Collections.max(bids, dateComparator).getTimestamp();
    return new OrderBook(timeStamp, asks, bids);

  }

  @Override
  public Trades getTrades(String tradableIdentifier, String currency, Object... args) throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException {

    String currencyPair = KrakenUtils.createKrakenCurrencyPair(tradableIdentifier, currency);
    KrakenTradesResult krakenTrades = kraken.getTrades(currencyPair);
    if (krakenTrades.getError().length > 0) {
      throw new ExchangeException(krakenTrades.getError().toString());
    }
    Trades trades = KrakenAdapters.adaptTrades(krakenTrades.getResult().getTradesPerCurrencyPair(currencyPair), currency, tradableIdentifier, krakenTrades.getResult().getLast());
    return trades;
  }

}
