package com.xeiam.xchange.mintpal.service.polling;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import si.mazi.rescu.RestProxyFactory;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.exceptions.ExchangeException;
import com.xeiam.xchange.mintpal.MintPal;
import com.xeiam.xchange.mintpal.MintPalAdapters;
import com.xeiam.xchange.mintpal.dto.MintPalBaseResponse;
import com.xeiam.xchange.service.BaseExchangeService;
import com.xeiam.xchange.service.polling.BasePollingService;

/**
 * @author jamespedwards42
 */
public class MintPalBasePollingService<T extends MintPal> extends BaseExchangeService implements BasePollingService {

  protected T mintPal;

  /**
   * Constructor
   *
   * @param type
   * @param exchangeSpecification
   */
  public MintPalBasePollingService(final Class<T> type, final Exchange exchange) {

    super(exchange);
    mintPal = RestProxyFactory.createProxy(type, exchange.getExchangeSpecification().getSslUri());
  }

  @Override
  public List<CurrencyPair> getExchangeSymbols() throws IOException {

    List<CurrencyPair> currencyPairs = new ArrayList<CurrencyPair>();
    currencyPairs.addAll(MintPalAdapters.adaptCurrencyPairs(handleRespone(mintPal.getAllTickers())));

    return currencyPairs;
  }

  public <R> R handleRespone(final MintPalBaseResponse<R> response) {

    if (!response.getStatus().equals("success")) {
      throw new ExchangeException(response.getMessage());
    }

    return response.getData();
  }
}
