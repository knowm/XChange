package org.knowm.xchange.vaultoro.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.vaultoro.VaultoroException;
import org.knowm.xchange.vaultoro.dto.marketdata.VaultoroOrderBook;
import org.knowm.xchange.vaultoro.dto.marketdata.VaultoroTrade;

public class VaultoroMarketDataServiceRaw extends VaultoroBaseService {

  /**
   * Constructor
   *
   * @param exchange
   */
  public VaultoroMarketDataServiceRaw(Exchange exchange) {

    super(exchange);
  }

  public BigDecimal getLast(org.knowm.xchange.currency.CurrencyPair currencyPair)
      throws VaultoroException, IOException {

    return vaultoro.getLatest();
  }

  public List<VaultoroOrderBook> getVaultoroOrderBook(
      org.knowm.xchange.currency.CurrencyPair currencyPair) throws VaultoroException, IOException {

    return vaultoro.getVaultoroOrderBook().getData();
  }

  public List<VaultoroTrade> getVaultoroTrades(org.knowm.xchange.currency.CurrencyPair currencyPair)
      throws VaultoroException, IOException {

    return vaultoro.getVaultoroTrades("month");
  }

  public List<org.knowm.xchange.currency.CurrencyPair> getExchangeSymbols() throws IOException {

    // TODO put this in the vautoro.json file in resources and call a real endpoint for the data in
    // addition
    List<org.knowm.xchange.currency.CurrencyPair> pairs = new ArrayList<>();
    pairs.add(org.knowm.xchange.currency.CurrencyPair.build("GLD", "BTC"));
    return pairs;
  }
}
