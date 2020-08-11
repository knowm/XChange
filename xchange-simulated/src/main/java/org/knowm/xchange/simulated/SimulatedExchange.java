package org.knowm.xchange.simulated;

import static java.math.BigDecimal.ZERO;

import java.io.IOException;
import java.util.Collection;
import org.apache.commons.lang3.StringUtils;
import org.knowm.xchange.BaseExchange;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.meta.CurrencyPairMetaData;
import org.knowm.xchange.exceptions.CurrencyPairNotValidException;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.exceptions.ExchangeSecurityException;
import si.mazi.rescu.SynchronizedValueFactory;

/**
 * A simple, in-memory implementation which mocks out the main elements of the XChange generic API
 * in a consistent way. The effect is to create a virtual "exchange" that you can connect to from
 * multiple threads and simulate a real exchange. Intended for integration testing of higher order
 * components.
 *
 * <p>This is not remotely suitable for use as a real-world exchange. The concurrency is extremely
 * coarse-grained and most data transforms involve data mutation with no . If any errors occur
 * midway through a trade, they are likely to leave the system in an inconsistent state. And nothing
 * gets saved to a database anyway.
 *
 * <p>
 *
 * <p>If you start using this for running a real exchange, you will <a
 * href="https://www.reddit.com/r/nanotrade/comments/83hosf/we_have_suffered_a_stolen/">suffer a
 * stolen</a>.
 *
 * @author Graham Crockford
 */
public class SimulatedExchange extends BaseExchange {

  /**
   * Allows the scope of the simulated exchange to be controlled. Pass to {@link
   * ExchangeSpecification#setExchangeSpecificParametersItem(String, Object)} to choose one of:
   *
   * <ul>
   *   <li>{@code new MatchingEngineFactory()} - <strong>default</strong>. Each instance of {@link
   *       SimulatedExchange} works on a different virtual exchange with its own set of order books,
   *       thus the simulated exchange is single-user. Recommended for unit testing.
   *   <li>An existing, shared instance of {@code MatchingEngineFactory} - Create your own factory
   *       and share it between {@link SimulatedExchange} instances to allow those specific
   *       instances to share the same order books and thus trade against each other. Recommended
   *       for integration testing.
   * </ul>
   */
  public static final String ENGINE_FACTORY_PARAM = "MatchingEngineFactory";

  /**
   * As with {@link #ENGINE_FACTORY_PARAM}, provides a default unshared but optionally shared
   * instance of {@link AccountFactory}.
   */
  public static final String ACCOUNT_FACTORY_PARAM = "AccountFactory";

  /** Provides a {@link SimulatedExchangeOperationListener}. */
  public static final String ON_OPERATION_PARAM = "OnExchangeOperation";

  private MatchingEngineFactory engineFactory;
  private AccountFactory accountFactory;
  private SimulatedExchangeOperationListener exceptionThrower;

  @Override
  public SynchronizedValueFactory<Long> getNonceFactory() {
    throw new UnsupportedOperationException("Nonce factory is not used.");
  }

  @Override
  public ExchangeSpecification getDefaultExchangeSpecification() {
    ExchangeSpecification exchangeSpecification =
        new ExchangeSpecification(this.getClass().getCanonicalName());
    exchangeSpecification.setExchangeName("Simulated");
    exchangeSpecification.setExchangeDescription(
        "A simulated exchange for integration testing purposes.");
    AccountFactory accountFactory = new AccountFactory();
    exchangeSpecification.setExchangeSpecificParametersItem(
        ENGINE_FACTORY_PARAM, new MatchingEngineFactory(accountFactory));
    exchangeSpecification.setExchangeSpecificParametersItem(ACCOUNT_FACTORY_PARAM, accountFactory);
    exchangeSpecification.setExchangeSpecificParametersItem(
        ON_OPERATION_PARAM, (SimulatedExchangeOperationListener) () -> {});
    return exchangeSpecification;
  }

  @Override
  protected void initServices() {
    engineFactory =
        (MatchingEngineFactory)
            exchangeSpecification.getExchangeSpecificParametersItem(ENGINE_FACTORY_PARAM);
    accountFactory =
        (AccountFactory)
            exchangeSpecification.getExchangeSpecificParametersItem(ACCOUNT_FACTORY_PARAM);
    exceptionThrower =
        (SimulatedExchangeOperationListener)
            exchangeSpecification.getExchangeSpecificParametersItem(ON_OPERATION_PARAM);
    tradeService = new SimulatedTradeService(this);
    marketDataService = new SimulatedMarketDataService(this);
    accountService = new SimulatedAccountService(this);
  }

  @Override
  public void remoteInit() throws IOException, ExchangeException {
    if (StringUtils.isNotEmpty(exchangeSpecification.getApiKey()))
      getAccount().initialize(getExchangeMetaData().getCurrencies().keySet());
  }

  Account getAccount() {
    if (StringUtils.isEmpty(exchangeSpecification.getApiKey()))
      throw new ExchangeSecurityException("API key required for account access");
    return accountFactory.get(exchangeSpecification.getApiKey());
  }

  void maybeThrow() throws IOException {
    exceptionThrower.onSimulatedExchangeOperation();
  }

  Collection<MatchingEngine> getEngines() {
    return engineFactory.engines();
  }

  MatchingEngine getEngine(CurrencyPair currencyPair) {
    CurrencyPairMetaData currencyPairMetaData =
        getExchangeMetaData().getCurrencyPairs().get(currencyPair);
    if (currencyPairMetaData == null) {
      throw new CurrencyPairNotValidException(
          "Currency pair " + currencyPair + " not known", currencyPair);
    }
    return engineFactory.create(
        currencyPair,
        currencyPairMetaData == null ? 8 : currencyPairMetaData.getPriceScale(),
        currencyPairMetaData == null ? ZERO : currencyPairMetaData.getMinimumAmount());
  }

  @Override
  public SimulatedMarketDataService getMarketDataService() {
    return (SimulatedMarketDataService) super.getMarketDataService();
  }

  @Override
  public SimulatedAccountService getAccountService() {
    return (SimulatedAccountService) super.getAccountService();
  }

  @Override
  public SimulatedTradeService getTradeService() {
    return (SimulatedTradeService) super.getTradeService();
  }
}
