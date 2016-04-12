package com.xeiam.xchange.vaultoro.service.polling;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.marketdata.OrderBook;
import com.xeiam.xchange.dto.marketdata.Ticker;
import com.xeiam.xchange.dto.marketdata.Trades;
import com.xeiam.xchange.exceptions.ExchangeException;
import com.xeiam.xchange.exceptions.NotAvailableFromExchangeException;
import com.xeiam.xchange.exceptions.NotYetImplementedForExchangeException;
import com.xeiam.xchange.service.polling.marketdata.PollingMarketDataService;
import com.xeiam.xchange.vaultoro.VaultoroAdapters;
import com.xeiam.xchange.vaultoro.dto.marketdata.VaultoroOrder;
import com.xeiam.xchange.vaultoro.dto.marketdata.VaultoroOrderBook;
import com.xeiam.xchange.vaultoro.dto.marketdata.VaultoroTrade;

/**
 * <p>
 * Implementation of the market data service for Bittrex
 * </p>
 * <ul>
 * <li>Provides access to various market data values</li>
 * </ul>
 */
public class VaultoroMarketDataService extends VaultoroMarketDataServiceRaw implements PollingMarketDataService {

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
  public OrderBook getOrderBook(CurrencyPair arg0, Object... arg1)
      throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {

    List<VaultoroOrderBook> vaultoroOrderBooks = super.getVaultoroOrderBook(arg0);

    List<VaultoroOrder> asks = new ArrayList<VaultoroOrder>();
    List<VaultoroOrder> bids = new ArrayList<VaultoroOrder>();

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
  public Ticker getTicker(CurrencyPair arg0, Object... arg1)
      throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {

    BigDecimal latest = super.getLast(arg0);
    return VaultoroAdapters.adaptVaultoroLatest(latest);
  }

  @Override
  public Trades getTrades(CurrencyPair arg0, Object... arg1)
      throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {

    List<VaultoroTrade> vaultoroTrades = super.getVaultoroTrades(arg0);
    return VaultoroAdapters.adaptVaultoroTransactions(vaultoroTrades, arg0);
  }

}
