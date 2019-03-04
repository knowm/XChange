package org.knowm.xchange.simulated;

import org.knowm.xchange.BaseExchange;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.meta.CurrencyPairMetaData;
import org.knowm.xchange.exceptions.CurrencyPairNotValidException;

import si.mazi.rescu.SynchronizedValueFactory;

public class SimulatedExchange extends BaseExchange {

  /**
   * Allows the scope of the simulated exchange to be controlled.  Pass to
   * {@link ExchangeSpecification#setExchangeSpecificParametersItem(String, Object)}
   * to choose one of:
   *
   * <ul>
   *  <li>{@code new MatchingEngineFactory()} - <strong>default</strong>. Each instance
   *  of {@link SimulatedExchange} works on a different virtual exchange with its own
   *  set of order books, thus the simulated exchange is single-user. Recommended
   *  for unit testing.</li>
   *  <li>An existing, shared instance of {@code MatchingEngineFactory} - Create your
   *  own factory and share it between {@link SimulatedExchange} instances to allow
   *  those specific instances to share the same order books and thus trade against
   *  each other. Recommended for integration testing.</li>
   *  <li>{@code MatchingEngineFactory.INSTANCE} - . A single,
   *  static set of matching engines. All {@link SimulatedExchange} instances that
   *  use this in a JVM process will share the same set of matching engines and thus
   *  trade against each other. Suitable for emulating volume trading sitations but
   *  can be risky in tests where it means that trade state bleeds between tests.</li>
   * </ul>
   */
  public static final String ENGINE_FACTORY_PARAM = "MatchingEngineFactory";

  private MatchingEngineFactory engineFactory;

  @Override
  public SynchronizedValueFactory<Long> getNonceFactory() {
    throw new UnsupportedOperationException("Nonce factory is not used.");
  }

  @Override
  public ExchangeSpecification getDefaultExchangeSpecification() {
    ExchangeSpecification exchangeSpecification =
        new ExchangeSpecification(this.getClass().getCanonicalName());
    exchangeSpecification.setExchangeName("Simulated");
    exchangeSpecification.setExchangeDescription("A simulated exchange for integration testing purposes.");
    exchangeSpecification.setExchangeSpecificParametersItem(ENGINE_FACTORY_PARAM, new MatchingEngineFactory());
    return exchangeSpecification;
  }

  @Override
  protected void initServices() {
    engineFactory = (MatchingEngineFactory) exchangeSpecification.getExchangeSpecificParametersItem(ENGINE_FACTORY_PARAM);
    tradeService = new SimulatedTradeService(this);
    marketDataService = new SimulatedMarketDataService(this);
  }

  MatchingEngine getEngine(CurrencyPair currencyPair) {
    CurrencyPairMetaData currencyPairMetaData = getExchangeMetaData()
        .getCurrencyPairs()
        .get(currencyPair);
    if (currencyPairMetaData == null) {
      throw new CurrencyPairNotValidException("Currency pair " + currencyPair + " not known", currencyPair);
    }
    return engineFactory.create(
        currencyPair,
        currencyPairMetaData == null ? 8 : currencyPairMetaData.getPriceScale());
  }
}
