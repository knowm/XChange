package org.knowm.xchange.examples.enigma.marketdata;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lombok.extern.slf4j.Slf4j;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.enigma.dto.marketdata.EnigmaProduct;
import org.knowm.xchange.enigma.dto.marketdata.EnigmaProductMarketData;
import org.knowm.xchange.enigma.service.EnigmaMarketDataServiceRaw;
import org.knowm.xchange.examples.enigma.EnigmaDemoUtils;
import org.knowm.xchange.service.marketdata.MarketDataService;
import org.knowm.xchange.service.marketdata.params.CurrencyPairsParam;

@Slf4j
public class EnigmaMarketDataDemo {

  public static void main(String[] args) throws IOException {
    Exchange enigma = EnigmaDemoUtils.createExchange();

    MarketDataService marketDataService = enigma.getMarketDataService();
    generic(marketDataService);
    raw((EnigmaMarketDataServiceRaw) marketDataService);
  }

  private static void generic(MarketDataService marketDataService) throws IOException {

    OrderBook orderBook = marketDataService.getOrderBook(CurrencyPair.BTC_USD);

    Trades trades = marketDataService.getTrades(CurrencyPair.BTC_USD);
    log.info("Trades data :{}", trades.toString());

    Ticker ticker = marketDataService.getTicker(CurrencyPair.BTC_USD);
    log.info("Ticker data :{}", ticker.toString());

    Set<CurrencyPair> someCurrencyPairs = new HashSet<>();
    someCurrencyPairs.add(new CurrencyPair("BTC", "USD"));
    someCurrencyPairs.add(new CurrencyPair("BTC", "EUR"));
    List<Ticker> tickers =
        marketDataService.getTickers((CurrencyPairsParam) () -> someCurrencyPairs);
    for (Ticker tickerElement : tickers) {
      log.info("Tickers data :{}", tickerElement.toString());
    }
  }

  private static void raw(EnigmaMarketDataServiceRaw serviceRaw) throws IOException {
    List<EnigmaProduct> products = serviceRaw.getProducts();
    products.forEach(product -> log.info(product.toString()));

    EnigmaProductMarketData productMarketData =
        serviceRaw.getProductMarketData(products.get(0).getProductId());
    log.info("market data :{}", productMarketData.toString());
  }
}
