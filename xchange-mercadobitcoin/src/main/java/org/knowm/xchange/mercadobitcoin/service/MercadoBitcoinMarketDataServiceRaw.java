package org.knowm.xchange.mercadobitcoin.service;

import java.io.IOException;
import java.math.BigDecimal;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.client.ExchangeRestProxyBuilder;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.mercadobitcoin.MercadoBitcoin;
import org.knowm.xchange.mercadobitcoin.MercadoBitcoinUtils;
import org.knowm.xchange.mercadobitcoin.dto.marketdata.MercadoBitcoinOrderBook;
import org.knowm.xchange.mercadobitcoin.dto.marketdata.MercadoBitcoinTicker;
import org.knowm.xchange.mercadobitcoin.dto.marketdata.MercadoBitcoinTransaction;

/** @author Felipe Micaroni Lalli */
public class MercadoBitcoinMarketDataServiceRaw extends MercadoBitcoinBaseService {

  private final MercadoBitcoin mercadoBitcoin;

  /**
   * Constructor
   *
   * @param exchange
   */
  public MercadoBitcoinMarketDataServiceRaw(Exchange exchange) {
    super(exchange);
    this.mercadoBitcoin =
        ExchangeRestProxyBuilder.forInterface(
                MercadoBitcoin.class, exchange.getExchangeSpecification())
            .build();
  }

  public MercadoBitcoinOrderBook getMercadoBitcoinOrderBook(CurrencyPair currencyPair)
      throws IOException {

    MercadoBitcoinUtils.verifyCurrencyPairAvailability(currencyPair);
    return mercadoBitcoin.getOrderBook(currencyPair.base.getSymbol());
  }

  public MercadoBitcoinTicker getMercadoBitcoinTicker(CurrencyPair currencyPair)
      throws IOException {
    MercadoBitcoinUtils.verifyCurrencyPairAvailability(currencyPair);
    return mercadoBitcoin.getTicker(currencyPair.base.getSymbol());
  }

  public MercadoBitcoinTransaction[] getMercadoBitcoinTransactions(
      CurrencyPair currencyPair, Object... args) throws IOException {

    MercadoBitcoinUtils.verifyCurrencyPairAvailability(currencyPair);

    MercadoBitcoinTransaction[] transactions;

    if (args.length == 0) {

      // default values: offset=0, limit=100
      transactions = mercadoBitcoin.getTransactions(currencyPair.base.getSymbol());

    } else if (args.length == 1) {
      BigDecimal time = new BigDecimal((Long) args[0]);
      transactions =
          mercadoBitcoin.getTransactions(currencyPair.base.getSymbol(), time.longValue() / 1000L);

    } else if (args.length == 2) {
      BigDecimal timeStart = new BigDecimal((Long) args[0]);
      BigDecimal timeEnd = new BigDecimal((Long) args[1]);

      transactions =
          mercadoBitcoin.getTransactions(
              currencyPair.base.getSymbol(),
              timeStart.longValue() / 1000L,
              timeEnd.longValue() / 1000L);
    } else {
      throw new ExchangeException("Invalid argument length. Must be 0, 1 or 2.");
    }
    return transactions;
  }
}
