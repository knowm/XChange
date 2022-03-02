package org.knowm.xchange.enigma.service;

import java.io.IOException;
import java.util.List;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.enigma.dto.marketdata.EnigmaOrderBook;
import org.knowm.xchange.enigma.dto.marketdata.EnigmaProduct;
import org.knowm.xchange.enigma.dto.marketdata.EnigmaProductMarketData;
import org.knowm.xchange.enigma.dto.marketdata.EnigmaTicker;
import org.knowm.xchange.enigma.dto.marketdata.EnigmaTransaction;
import org.knowm.xchange.enigma.model.EnigmaException;

public class EnigmaMarketDataServiceRaw extends EnigmaBaseService {

  public EnigmaMarketDataServiceRaw(Exchange exchange) {
    super(exchange);
  }

  public EnigmaProductMarketData getProductMarketData(int productId) throws IOException {
    return this.enigmaAuthenticated.getProductMarketData(accessToken(), productId);
  }

  public List<EnigmaProduct> getProducts() throws IOException {
    return this.enigmaAuthenticated.getProducts(accessToken());
  }

  public EnigmaTicker getEnigmaTicker(CurrencyPair currencyPair) throws IOException {

    List<EnigmaProduct> products = getProducts();
    Integer productId =
        products.stream()
            .filter(
                product ->
                    product.getProductName().equals(currencyPair.toString().replace("/", "-")))
            .map(EnigmaProduct::getProductId)
            .findFirst()
            .orElseThrow(() -> new EnigmaException("Currency pair not found"));

    return this.enigmaAuthenticated.getTicker(accessToken(), productId);
  }

  public EnigmaOrderBook getEnigmaOrderBook(String pair) throws IOException {
    return this.enigmaAuthenticated.getOrderBook(accessToken(), pair);
  }

  public EnigmaTransaction[] getEnigmaTransactions() throws IOException {
    return this.enigmaAuthenticated.getTransactions(
        accessToken(),
        this.exchange
            .getExchangeSpecification()
            .getExchangeSpecificParametersItem("infra")
            .toString());
  }
}
