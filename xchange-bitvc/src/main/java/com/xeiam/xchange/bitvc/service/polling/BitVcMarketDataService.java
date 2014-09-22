package com.xeiam.xchange.bitvc.service.polling;

import java.io.IOException;

import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.bitvc.BitVcAdapters;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.marketdata.OrderBook;
import com.xeiam.xchange.dto.marketdata.Ticker;
import com.xeiam.xchange.dto.marketdata.Trades;
import com.xeiam.xchange.service.polling.PollingMarketDataService;

public class BitVcMarketDataService extends BitVcMarketDataServiceRaw implements PollingMarketDataService {

  public BitVcMarketDataService(ExchangeSpecification exchangeSpecification) {

    super(exchangeSpecification);
  }

  @Override
  public Ticker getTicker(CurrencyPair currencyPair, Object... args) throws IOException {

    return BitVcAdapters.adaptTicker(getBitVcTicker(currencyPair.baseSymbol.toLowerCase()), currencyPair);
  }

  @Override
  public OrderBook getOrderBook(CurrencyPair currencyPair, Object... args) throws IOException {

    return BitVcAdapters.adaptOrderBook(getBitVcDepth(currencyPair.baseSymbol.toLowerCase()), currencyPair);
  }

  @Override
  public Trades getTrades(CurrencyPair currencyPair, Object... args) throws IOException {

    return BitVcAdapters.adaptTrades(getBitVcDetail(currencyPair.baseSymbol.toLowerCase()), currencyPair);
  }

}
