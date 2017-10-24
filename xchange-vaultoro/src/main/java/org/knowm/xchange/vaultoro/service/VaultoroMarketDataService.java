package org.knowm.xchange.vaultoro.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.exceptions.NotAvailableFromExchangeException;
import org.knowm.xchange.exceptions.NotYetImplementedForExchangeException;
import org.knowm.xchange.service.marketdata.MarketDataService;
import org.knowm.xchange.vaultoro.VaultoroAdapters;
import org.knowm.xchange.vaultoro.dto.marketdata.VaultoroOrder;
import org.knowm.xchange.vaultoro.dto.marketdata.VaultoroOrderBook;
import org.knowm.xchange.vaultoro.dto.marketdata.VaultoroTrade;

/**
 * <p>
 * Implementation of the market data service for Bittrex
 * </p>
 * <ul>
 * <li>Provides access to various market data values</li>
 * </ul>
 */
public class VaultoroMarketDataService extends VaultoroMarketDataServiceRaw implements MarketDataService {

  /**
   * Constructor
   *
   * @param exchange
   */
  public VaultoroMarketDataService(Exchange exchange) {

    super(exchange);
  }

  @Override
  public List<CurrencyPair> getExchangeSymbols() throws IOException {

    return super.getExchangeSymbols();
  }

  @Override
  public OrderBook getOrderBook(CurrencyPair arg0,
      Object... arg1) throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {

    List<VaultoroOrderBook> vaultoroOrderBooks = super.getVaultoroOrderBook(arg0);

    List<VaultoroOrder> asks = new ArrayList<>();
    List<VaultoroOrder> bids = new ArrayList<>();

    for (VaultoroOrderBook vaultoroOrderBook : vaultoroOrderBooks) {
      asks.addAll(vaultoroOrderBook.getSells());
      bids.addAll(vaultoroOrderBook.getBuys());
    }

    VaultoroOrderBook vaultoroOrderBook = new VaultoroOrderBook();
    vaultoroOrderBook.setB(bids);
    vaultoroOrderBook.setS(asks);

    return VaultoroAdapters.adaptVaultoroOrderBook(vaultoroOrderBook, arg0);
  }

  @Override
  public Ticker getTicker(CurrencyPair arg0,
      Object... arg1) throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {

    BigDecimal latest = super.getLast(arg0);
    return VaultoroAdapters.adaptVaultoroLatest(latest);
  }

  @Override
  public Trades getTrades(CurrencyPair arg0,
      Object... arg1) throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {

    List<VaultoroTrade> vaultoroTrades = super.getVaultoroTrades(arg0);
    return VaultoroAdapters.adaptVaultoroTransactions(vaultoroTrades, arg0);
  }

}
