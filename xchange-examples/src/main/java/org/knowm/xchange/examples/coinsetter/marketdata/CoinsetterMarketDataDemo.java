package org.knowm.xchange.examples.coinsetter.marketdata;

import java.io.IOException;
import java.math.BigDecimal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.coinsetter.CoinsetterException;
import org.knowm.xchange.coinsetter.CoinsetterExchange;
import org.knowm.xchange.coinsetter.dto.marketdata.CoinsetterLast;
import org.knowm.xchange.coinsetter.dto.marketdata.CoinsetterListDepth;
import org.knowm.xchange.coinsetter.dto.marketdata.CoinsetterPairedDepth;
import org.knowm.xchange.coinsetter.dto.marketdata.CoinsetterQuote;
import org.knowm.xchange.coinsetter.dto.marketdata.CoinsetterTicker;
import org.knowm.xchange.coinsetter.service.polling.CoinsetterMarketDataServiceRaw;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.service.polling.marketdata.PollingMarketDataService;

public class CoinsetterMarketDataDemo {

  private static final Logger log = LoggerFactory.getLogger(CoinsetterMarketDataDemo.class);

  public static void main(String[] args) throws IOException {

    Exchange coinsetter = ExchangeFactory.INSTANCE.createExchange(CoinsetterExchange.class.getName());
    generic(coinsetter);
    raw(coinsetter);
  }

  private static void generic(Exchange exchange) throws IOException {

    PollingMarketDataService marketDataService = exchange.getPollingMarketDataService();

    Ticker ticker = marketDataService.getTicker(CurrencyPair.BTC_USD);
    log.info("Ticker: {}", ticker);

    OrderBook orderBook = marketDataService.getOrderBook(CurrencyPair.BTC_USD);
    log.info("OrderBook: {}", orderBook);

    orderBook = marketDataService.getOrderBook(CurrencyPair.BTC_AUD, "COINSETTER");
    log.info("OrderBook(COINSETTER): {}", orderBook);

    orderBook = marketDataService.getOrderBook(CurrencyPair.BTC_AUD, "COINSETTER", 5);
    log.info("OrderBook(COINSETTER, 5): {}", orderBook);

    orderBook = marketDataService.getOrderBook(CurrencyPair.BTC_AUD, null, 5);
    log.info("OrderBook(null, 5): {}", orderBook);

    orderBook = marketDataService.getOrderBook(CurrencyPair.BTC_AUD, null, null);
    log.info("OrderBook(null, null): {}", orderBook);
  }

  private static void raw(Exchange exchange) throws IOException {

    CoinsetterMarketDataServiceRaw marketDataService = (CoinsetterMarketDataServiceRaw) exchange.getPollingMarketDataService();

    CoinsetterLast last = marketDataService.getCoinsetterLast();
    log.info("Coinsetter last: {}", last);

    last = marketDataService.getCoinsetterLast(5);
    log.info("Coinsetter last 5: {}", last);

    CoinsetterTicker ticker = marketDataService.getCoinsetterTicker();
    log.info("Coinsetter ticker: {}", ticker);

    CoinsetterPairedDepth pairedDepth = marketDataService.getCoinsetterPairedDepth(10, "SMART");
    log.info("Coinsetter paired depth: {}", pairedDepth);

    CoinsetterListDepth fullDepth = marketDataService.getCoinsetterFullDepth("SMART");
    log.info("Coinsetter full depth: {}", fullDepth);

    CoinsetterQuote quoteBid = marketDataService.getCoinsetterQuote(new BigDecimal("5"), "BTCUSD");
    log.info("Coinsetter quote bid: {}", quoteBid);

    CoinsetterQuote quoteAsk = marketDataService.getCoinsetterQuote(new BigDecimal("-5"), "BTCUSD");
    log.info("Coinsetter quote ask: {}", quoteAsk);

    try {
      // Invalid quoting quantity.
      marketDataService.getCoinsetterQuote(new BigDecimal("1"), "BTCUSD");
    } catch (CoinsetterException e) {
      log.info("Message: {}", e.getMessage());
    }

  }

}
