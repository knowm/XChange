package com.xeiam.xchange.itbit.v1.service.polling;

import java.io.IOException;

import si.mazi.rescu.RestProxyFactory;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.exceptions.ExchangeException;
import com.xeiam.xchange.exceptions.NotAvailableFromExchangeException;
import com.xeiam.xchange.exceptions.NotYetImplementedForExchangeException;
import com.xeiam.xchange.itbit.v1.ItBit;
import com.xeiam.xchange.itbit.v1.dto.marketdata.ItBitDepth;
import com.xeiam.xchange.itbit.v1.dto.marketdata.ItBitTicker;
import com.xeiam.xchange.itbit.v1.dto.marketdata.ItBitTrade;

public class ItBitMarketDataServiceRaw extends ItBitBasePollingService {

  protected final ItBit itBitPublic;

  /**
   * Constructor
   *
   * @param exchange
   */
  public ItBitMarketDataServiceRaw(Exchange exchange) {

    super(exchange);
    itBitPublic = RestProxyFactory.createProxy(ItBit.class, exchange.getExchangeSpecification().getSslUri());
  }

  public ItBitTicker getItBitTicker(CurrencyPair currencyPair) throws ExchangeException, NotAvailableFromExchangeException,
      NotYetImplementedForExchangeException, IOException {

    ItBitTicker ticker = itBit.getTicker(currencyPair.baseSymbol, currencyPair.counterSymbol);

    return ticker;
  }

  public ItBitDepth getItBitDepth(CurrencyPair currencyPair, Object... args) throws ExchangeException, NotAvailableFromExchangeException,
      NotYetImplementedForExchangeException, IOException {

    ItBitDepth depth = itBitPublic.getDepth(currencyPair.baseSymbol, currencyPair.counterSymbol);

    return depth;
  }

  public ItBitTrade[] getItBitTrades(CurrencyPair currencyPair, Object... args) throws ExchangeException, NotAvailableFromExchangeException,
      NotYetImplementedForExchangeException, IOException {

    long since = 0;
    if (args.length == 1) {
      since = ((Number) args[0]).longValue();
    }

    ItBitTrade[] trades = itBitPublic.getTrades(currencyPair.baseSymbol, currencyPair.counterSymbol, since);
    return trades;
  }
}
