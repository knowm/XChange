package com.xeiam.xchange.itbit.v1.service.polling;

import java.io.IOException;

import si.mazi.rescu.RestProxyFactory;

import com.xeiam.xchange.ExchangeException;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.NotAvailableFromExchangeException;
import com.xeiam.xchange.NotYetImplementedForExchangeException;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.itbit.v1.ItBit;
import com.xeiam.xchange.itbit.v1.dto.marketdata.ItBitDepth;
import com.xeiam.xchange.itbit.v1.dto.marketdata.ItBitTrade;

public class ItBitMarketDataServiceRaw extends ItBitBasePollingService {

  protected final ItBit itBit;

  /**
   * @param exchangeSpecification The {@link ExchangeSpecification}
   */
  public ItBitMarketDataServiceRaw(ExchangeSpecification exchangeSpecification) {

    super(exchangeSpecification);
    itBit = RestProxyFactory.createProxy(ItBit.class, exchangeSpecification.getSslUri());
  }

  public ItBitDepth getItBitDepth(CurrencyPair currencyPair, Object... args) throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {

    ItBitDepth depth = itBit.getDepth(currencyPair.baseSymbol, currencyPair.counterSymbol);

    return depth;
  }

  public ItBitTrade[] getItBitTrades(CurrencyPair currencyPair, Object... args) throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {

    long since = 0;
    if (args.length == 1) {
      since = ((Number) args[0]).longValue();
    }

    ItBitTrade[] trades = itBit.getTrades(currencyPair.baseSymbol, currencyPair.counterSymbol, since);
    return trades;
  }
}
