package org.knowm.xchange.examples.enigma.marketdata;

import lombok.extern.slf4j.Slf4j;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.enigma.dto.marketdata.EnigmaProduct;
import org.knowm.xchange.enigma.dto.marketdata.EnigmaProductMarketData;
import org.knowm.xchange.enigma.service.EnigmaMarketDataServiceRaw;
import org.knowm.xchange.examples.enigma.EnigmaDemoUtils;
import org.knowm.xchange.service.marketdata.MarketDataService;

import java.io.IOException;
import java.util.List;

@Slf4j
public class EnigmaMarketDataDemo {

  public static void main(String[] args) throws IOException {
    Exchange enigma = EnigmaDemoUtils.createExchange();

    MarketDataService marketDataService = enigma.getMarketDataService();
    raw((EnigmaMarketDataServiceRaw) marketDataService);
  }

  private static void raw(EnigmaMarketDataServiceRaw serviceRaw) throws IOException {
    List<EnigmaProduct> products = serviceRaw.getProducts();
    products.forEach(product -> log.info(product.toString()));

    EnigmaProductMarketData productMarketData =
        serviceRaw.getProductMarketData(products.get(0).getProductId());
    log.info("market data :{}", productMarketData.toString());
  }
}
