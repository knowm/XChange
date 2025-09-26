package org.knowm.xchange.bitso.service;

import java.io.IOException;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.bitso.Bitso;
import org.knowm.xchange.bitso.BitsoJacksonObjectMapperFactory;
import org.knowm.xchange.bitso.dto.marketdata.BitsoAvailableBooks;
import org.knowm.xchange.bitso.dto.marketdata.BitsoOrderBook;
import org.knowm.xchange.bitso.dto.marketdata.BitsoTicker;
import org.knowm.xchange.bitso.dto.marketdata.BitsoTrades;
import org.knowm.xchange.client.ExchangeRestProxyBuilder;
import org.knowm.xchange.instrument.Instrument;

/**
 * @author Piotr Ładyżyński Updated for Bitso API v3
 */
public class BitsoMarketDataServiceRaw extends BitsoBaseService {

  private final Bitso bitso;

  public BitsoMarketDataServiceRaw(Exchange exchange) {
    super(exchange);
    this.bitso =
        ExchangeRestProxyBuilder.forInterface(Bitso.class, exchange.getExchangeSpecification())
            .clientConfigCustomizer(
                clientConfig ->
                    clientConfig.setJacksonObjectMapperFactory(
                        new BitsoJacksonObjectMapperFactory()))
            .build();
  }

  public BitsoAvailableBooks getBitsoAvailableBooks() throws IOException {
    return bitso.getAvailableBooks();
  }

  public BitsoOrderBook getBitsoOrderBook(Instrument pair) throws IOException {
    String book =
        pair.getBase().getCurrencyCode().toLowerCase()
            + "_"
            + pair.getCounter().getCurrencyCode().toLowerCase();
    return bitso.getOrderBook(book);
  }

  public BitsoTrades getBitsoTrades(Instrument pair, Object... args) throws IOException {
    String book =
        pair.getBase().getCurrencyCode().toLowerCase()
            + "_"
            + pair.getCounter().getCurrencyCode().toLowerCase();

    if (args.length == 0) {
      return bitso.getTrades(book);
    } else if (args.length >= 3) {
      String marker = args[0] != null ? args[0].toString() : null;
      String sort = args[1] != null ? args[1].toString() : null;
      Integer limit = args[2] != null ? Integer.valueOf(args[2].toString()) : null;
      return bitso.getTrades(book, marker, sort, limit);
    } else {
      return bitso.getTrades(book);
    }
  }

  public BitsoTicker getBitsoTicker(Instrument pair) throws IOException {
    String book =
        pair.getBase().getCurrencyCode().toLowerCase()
            + "_"
            + pair.getCounter().getCurrencyCode().toLowerCase();
    return bitso.getTicker(book);
  }
}
