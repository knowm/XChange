package com.xeiam.xchange.mercadobitcoin.service.polling;

import java.io.IOException;
import java.math.BigDecimal;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.currency.Currency;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.exceptions.ExchangeException;
import com.xeiam.xchange.exceptions.NotAvailableFromExchangeException;
import com.xeiam.xchange.mercadobitcoin.MercadoBitcoin;
import com.xeiam.xchange.mercadobitcoin.dto.marketdata.MercadoBitcoinOrderBook;
import com.xeiam.xchange.mercadobitcoin.dto.marketdata.MercadoBitcoinTicker;
import com.xeiam.xchange.mercadobitcoin.dto.marketdata.MercadoBitcoinTransaction;

import si.mazi.rescu.RestProxyFactory;

/**
 * @author Felipe Micaroni Lalli
 */
public class MercadoBitcoinMarketDataServiceRaw extends MercadoBitcoinBasePollingService {

  private final MercadoBitcoin mercadoBitcoin;

  /**
   * Constructor
   *
   * @param exchange
   */
  public MercadoBitcoinMarketDataServiceRaw(Exchange exchange) {

    super(exchange);
    this.mercadoBitcoin = RestProxyFactory.createProxy(MercadoBitcoin.class, exchange.getExchangeSpecification().getSslUri());
  }

  public MercadoBitcoinOrderBook getMercadoBitcoinOrderBook(CurrencyPair currencyPair) throws IOException {

    if (currencyPair.equals(CurrencyPair.BTC_BRL)) {
      return mercadoBitcoin.getOrderBookBTC();
    } else if (currencyPair.equals(new CurrencyPair(Currency.LTC, Currency.BRL))) {
      return mercadoBitcoin.getOrderBookLTC();
    } else {
      throw new NotAvailableFromExchangeException();
    }
  }

  public MercadoBitcoinTicker getMercadoBitcoinTicker(CurrencyPair currencyPair) throws IOException {

    if (currencyPair.equals(CurrencyPair.BTC_BRL)) {
      return mercadoBitcoin.getTickerBTC();
    } else if (currencyPair.equals(new CurrencyPair(Currency.LTC, Currency.BRL))) {
      return mercadoBitcoin.getTickerLTC();
    } else {
      throw new NotAvailableFromExchangeException();
    }
  }

  public MercadoBitcoinTransaction[] getMercadoBitcoinTransactions(CurrencyPair currencyPair, Object... args) throws IOException {

    MercadoBitcoinTransaction[] transactions;

    if (args.length == 0) {
      if (currencyPair.equals(CurrencyPair.BTC_BRL)) {
        transactions = mercadoBitcoin.getTransactionsBTC(); // default values: offset=0, limit=100
      } else if (currencyPair.equals(new CurrencyPair(Currency.LTC, Currency.BRL))) {
        transactions = mercadoBitcoin.getTransactionsLTC();
      } else {
        throw new NotAvailableFromExchangeException();
      }
    } else if (args.length == 1) {
      BigDecimal time = new BigDecimal((Long) args[0]);

      if (currencyPair.equals(CurrencyPair.BTC_BRL)) {
        transactions = mercadoBitcoin.getTransactionsBTC(time.longValue() / 1000L);
      } else if (currencyPair.equals(new CurrencyPair(Currency.LTC, Currency.BRL))) {
        transactions = mercadoBitcoin.getTransactionsLTC(time.longValue() / 1000L);
      } else {
        throw new NotAvailableFromExchangeException();
      }

    } else if (args.length == 2) {
      BigDecimal timeStart = new BigDecimal((Long) args[0]);
      BigDecimal timeEnd = new BigDecimal((Long) args[1]);

      if (currencyPair.equals(CurrencyPair.BTC_BRL)) {
        transactions = mercadoBitcoin.getTransactionsBTC(timeStart.longValue() / 1000L, timeEnd.longValue() / 1000L);
      } else if (currencyPair.equals(new CurrencyPair(Currency.LTC, Currency.BRL))) {
        transactions = mercadoBitcoin.getTransactionsLTC(timeStart.longValue() / 1000L, timeEnd.longValue() / 1000L);
      } else {
        throw new NotAvailableFromExchangeException();
      }

    } else {
      throw new ExchangeException("Invalid argument length. Must be 0, 1 or 2.");
    }
    return transactions;
  }
}
