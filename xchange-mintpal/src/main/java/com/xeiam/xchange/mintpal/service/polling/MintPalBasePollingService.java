package com.xeiam.xchange.mintpal.service.polling;

import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import si.mazi.rescu.RestProxyFactory;

import com.xeiam.xchange.ExchangeSpecification;
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
  private final Set<CurrencyPair> currencyPairs = new HashSet<CurrencyPair>();

  public MintPalBasePollingService(final Class<T> type, final ExchangeSpecification exchangeSpecification) {

    super(exchangeSpecification);
    mintPal = RestProxyFactory.createProxy(type, exchangeSpecification.getSslUri());
  }

  @Override
  public synchronized Collection<CurrencyPair> getExchangeSymbols() throws IOException {

    if (currencyPairs.isEmpty())
      currencyPairs.addAll(MintPalAdapters.adaptCurrencyPairs(handleRespone(mintPal.getAllTickers())));

    return currencyPairs;
  }

  public <R> R handleRespone(final MintPalBaseResponse<R> response) {

    if (!response.getStatus().equals("success"))
      throw new ExchangeException(response.getMessage());

    return response.getData();
  }
}
