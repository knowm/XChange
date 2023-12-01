package org.knowm.xchange.examples.blockchain.marketdata;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import java.io.IOException;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.examples.blockchain.BlockchainDemoUtils;
import org.knowm.xchange.instrument.Instrument;
import org.knowm.xchange.service.marketdata.MarketDataService;

public class BlockchainMarketDataDemo {
  private static final Exchange BLOCKCHAIN_EXCHANGE = BlockchainDemoUtils.createExchange();
  private static final ObjectMapper OBJECT_MAPPER =
      new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);

  public static void main(String[] args) throws IOException, InterruptedException {
    System.out.println("===== MARKETDATA SERVICE =====");
    marketDataServiceDemo();
  }

  private static void marketDataServiceDemo() throws IOException {
    MarketDataService marketDataService = BLOCKCHAIN_EXCHANGE.getMarketDataService();

    System.out.println("===== ORDERBOOK FOR BTC/USD =====");
    Instrument instrument = CurrencyPair.BTC_USD;
    OrderBook orders = marketDataService.getOrderBook(instrument);
    System.out.println(OBJECT_MAPPER.writeValueAsString(orders));
  }
}
