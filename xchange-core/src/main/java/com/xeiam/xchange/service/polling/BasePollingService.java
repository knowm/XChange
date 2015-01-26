package com.xeiam.xchange.service.polling;

import java.io.IOException;
import java.util.List;

import com.xeiam.xchange.currency.CurrencyPair;

public interface BasePollingService {

  public List<CurrencyPair> getExchangeSymbols() throws IOException;

}
