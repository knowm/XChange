package org.knowm.xchange.examples.acx;

import java.io.IOException;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.service.marketdata.MarketDataService;
import org.known.xchange.acx.AcxExchange;

public class AcxMarketServiceDemo {
  public static void main(String[] args) throws IOException {
    Exchange exchange = createTestExchange();
    generic(exchange);
  }

  public static Exchange createTestExchange() {
    Exchange exchange = ExchangeFactory.INSTANCE.createExchange(AcxExchange.class.getName());
    ExchangeSpecification spec = exchange.getExchangeSpecification();
    exchange.applySpecification(spec);
    return exchange;
  }

  private static void generic(Exchange exchange) throws IOException {
    MarketDataService service = exchange.getMarketDataService();

    Ticker ticker = service.getTicker(CurrencyPair.ETH_AUD);

    System.out.println("Last: " + ticker.getLast());
    System.out.println("Bid: " + ticker.getBid());
    System.out.println("Ask: " + ticker.getAsk());

    OrderBook orderBook = service.getOrderBook(CurrencyPair.ETH_AUD);
    System.out.println("Order book: " + orderBook);

    Trades trades = service.getTrades(CurrencyPair.ETH_AUD);
    System.out.println("First: " + trades.getTrades().get(0));
    System.out.println("Last: " + trades.getTrades().get(trades.getTrades().size() - 1));
  }
}
