package com.xeiam.xchange.vaultoro.service.polling;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.vaultoro.VaultoroException;
import com.xeiam.xchange.vaultoro.dto.marketdata.VaultoroOrderBook;
import com.xeiam.xchange.vaultoro.dto.marketdata.VaultoroTrade;

public class VaultoroMarketDataServiceRaw extends VaultoroBasePollingService {

  /**
   * Constructor
   *
   * @param exchange
   */
  public VaultoroMarketDataServiceRaw(Exchange exchange) {

    super(exchange);
  }

  public BigDecimal getLast(CurrencyPair currencyPair) throws VaultoroException, IOException {

    return vaultoro.getLatest();
  }

  public List<VaultoroOrderBook> getVaultoroOrderBook(CurrencyPair currencyPair) throws VaultoroException, IOException {

    return vaultoro.getVaultoroOrderBook().getData();
  }

  public List<VaultoroTrade> getVaultoroTrades(CurrencyPair currencyPair) throws VaultoroException, IOException {

    return vaultoro.getVaultoroTrades("month");
  }

}
