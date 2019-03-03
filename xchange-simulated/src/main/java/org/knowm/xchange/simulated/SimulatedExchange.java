package org.knowm.xchange.simulated;

import org.knowm.xchange.BaseExchange;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.meta.CurrencyPairMetaData;
import org.knowm.xchange.exceptions.CurrencyPairNotValidException;

import si.mazi.rescu.SynchronizedValueFactory;

public class SimulatedExchange extends BaseExchange {

  @Override
  public SynchronizedValueFactory<Long> getNonceFactory() {
    throw new UnsupportedOperationException("Nonce factory is not used.");
  }

  @Override
  public ExchangeSpecification getDefaultExchangeSpecification() {
    return new ExchangeSpecification(SimulatedExchange.class);
  }

  @Override
  protected void initServices() {
    tradeService = new SimulatedTradeService(this);
  }

  MatchingEngine getEngine(CurrencyPair currencyPair) {
    CurrencyPairMetaData currencyPairMetaData = getExchangeMetaData()
        .getCurrencyPairs()
        .get(currencyPair);
    if (currencyPairMetaData == null) {
      throw new CurrencyPairNotValidException("Currency pair " + currencyPair + " not known", currencyPair);
    }
    return MatchingEngine.create(
        currencyPair,
        currencyPairMetaData == null ? 8 : currencyPairMetaData.getPriceScale(),
        f -> {});
  }
}
