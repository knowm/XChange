package com.xeiam.xchange.bitcointoyou.service.polling;

import java.io.IOException;
import java.math.BigDecimal;

import si.mazi.rescu.RestProxyFactory;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.bitcointoyou.BitcoinToYou;
import com.xeiam.xchange.bitcointoyou.dto.marketdata.BitcoinToYouOrderBook;
import com.xeiam.xchange.bitcointoyou.dto.marketdata.BitcoinToYouTicker;
import com.xeiam.xchange.bitcointoyou.dto.marketdata.BitcoinToYouTransaction;
import com.xeiam.xchange.currency.Currencies;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.exceptions.ExchangeException;
import com.xeiam.xchange.exceptions.NotAvailableFromExchangeException;

/**
 * @author gnandiga
 * @author Felipe Micaroni Lalli
 */
public class BitcoinToYouMarketDataServiceRaw extends BitcoinToYouBasePollingService {

  private final BitcoinToYou bitcoinToYou;

  /**
   * Initialize common properties from the exchange specification
   *
   * @param exchangeSpecification The {@link com.xeiam.xchange.ExchangeSpecification}
   */
  public BitcoinToYouMarketDataServiceRaw(Exchange exchange) {

    super(exchange);
    this.bitcoinToYou = RestProxyFactory.createProxy(BitcoinToYou.class, exchange.getExchangeSpecification().getSslUri());
  }

  public BitcoinToYouOrderBook getBitcoinToYouOrderBook(CurrencyPair currencyPair) throws IOException {

    if (currencyPair.equals(CurrencyPair.BTC_BRL)) {
      return bitcoinToYou.getOrderBookBTC();
    } else if (currencyPair.equals(new CurrencyPair(Currencies.LTC, Currencies.BRL))) {
      return bitcoinToYou.getOrderBookLTC();
    } else {
      throw new NotAvailableFromExchangeException();
    }
  }

  public BitcoinToYouTicker getBitcoinToYouTicker(CurrencyPair currencyPair) throws IOException {

    if (currencyPair.equals(CurrencyPair.BTC_BRL)) {
      return bitcoinToYou.getTickerBTC();
    } else if (currencyPair.equals(new CurrencyPair(Currencies.LTC, Currencies.BRL))) {
      return bitcoinToYou.getTickerLTC();
    } else {
      throw new NotAvailableFromExchangeException();
    }
  }

  public BitcoinToYouTransaction[] getBitcoinToYouTransactions(CurrencyPair currencyPair, Object... args) throws IOException {

    BitcoinToYouTransaction[] transactions;

    if (args.length == 0) {
      if (currencyPair.equals(CurrencyPair.BTC_BRL)) {
        transactions = bitcoinToYou.getTransactions("BTC", null, null); // default values: offset=0, limit=100
      } else if (currencyPair.equals(new CurrencyPair(Currencies.LTC, Currencies.BRL))) {
        transactions = bitcoinToYou.getTransactions("LTC", null, null);
      } else {
        throw new NotAvailableFromExchangeException();
      }
    } else if (args.length == 1) {
      BigDecimal time = new BigDecimal((Long) args[0]);

      if (currencyPair.equals(CurrencyPair.BTC_BRL)) {
        transactions = bitcoinToYou.getTransactions("BTC", time.longValue(), null);
      } else if (currencyPair.equals(new CurrencyPair(Currencies.LTC, Currencies.BRL))) {
        transactions = bitcoinToYou.getTransactions("LTC", time.longValue(), null);
      } else {
        throw new NotAvailableFromExchangeException();
      }

    } else {
      throw new ExchangeException("Invalid argument length. Must be 0 or 1");
    }
    return transactions;
  }
}
