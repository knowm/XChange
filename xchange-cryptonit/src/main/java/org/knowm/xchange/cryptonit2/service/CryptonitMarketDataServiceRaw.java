package org.knowm.xchange.cryptonit2.service;

import java.io.IOException;
import javax.annotation.Nullable;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.cryptonit2.CryptonitV2;
import org.knowm.xchange.cryptonit2.dto.CryptonitException;
import org.knowm.xchange.cryptonit2.dto.marketdata.CryptonitOrderBook;
import org.knowm.xchange.cryptonit2.dto.marketdata.CryptonitTicker;
import org.knowm.xchange.cryptonit2.dto.marketdata.CryptonitTransaction;
import org.knowm.xchange.currency.CurrencyPair;
import si.mazi.rescu.RestProxyFactory;

/** @author gnandiga */
public class CryptonitMarketDataServiceRaw extends CryptonitBaseService {

  private final CryptonitV2 cryptonitV2;

  public CryptonitMarketDataServiceRaw(Exchange exchange) {

    super(exchange);
    this.cryptonitV2 =
        RestProxyFactory.createProxy(
            CryptonitV2.class, exchange.getExchangeSpecification().getSslUri(), getClientConfig());
  }

  public CryptonitTicker getCryptonitTicker(CurrencyPair pair) throws IOException {
    try {
      return cryptonitV2.getTicker(new CryptonitV2.Pair(pair));
    } catch (CryptonitException e) {
      throw handleError(e);
    }
  }

  public CryptonitOrderBook getCryptonitOrderBook(CurrencyPair pair) throws IOException {

    try {
      return cryptonitV2.getOrderBook(new CryptonitV2.Pair(pair));
    } catch (CryptonitException e) {
      throw handleError(e);
    }
  }

  public CryptonitTransaction[] getTransactions(CurrencyPair pair, @Nullable CryptonitTime time)
      throws IOException {

    try {
      return cryptonitV2.getTransactions(new CryptonitV2.Pair(pair), time);
    } catch (CryptonitException e) {
      throw handleError(e);
    }
  }

  public enum CryptonitTime {
    DAY,
    HOUR,
    MINUTE;

    @Override
    public String toString() {
      return super.toString().toLowerCase();
    }
  }
}
