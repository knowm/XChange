package org.knowm.xchange.examples;

import static org.fest.assertions.api.Assertions.assertThat;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

import org.junit.Assume;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.meta.CurrencyMetaData;
import org.knowm.xchange.dto.meta.CurrencyPairMetaData;
import org.knowm.xchange.dto.meta.ExchangeMetaData;
import org.knowm.xchange.exceptions.NotAvailableFromExchangeException;
import org.knowm.xchange.exceptions.NotYetImplementedForExchangeException;
import org.knowm.xchange.service.marketdata.MarketDataService;
import org.knowm.xchange.utils.retries.IPredicate;
import org.knowm.xchange.utils.retries.Retries;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RunWith(Parameterized.class)
public class AllIntegration {

  @Parameterized.Parameters(name = "{index}:{1}")
  public static Iterable<Object[]> data() {

    List<Object[]> exchangeClasses = new ArrayList<>();

    // Find every Exchange
    Reflections reflections = new Reflections("org.knowm.xchange");
    for (Class<? extends Exchange> exchangeClass : reflections.getSubTypesOf(Exchange.class)) {
      if (Modifier.isAbstract(exchangeClass.getModifiers())) {
        continue;
      }

      exchangeClasses.add(new Object[]{exchangeClass, exchangeClass.getSimpleName()});
    }

    return exchangeClasses;
  }

  @Parameterized.Parameter(0)
  public Class<? extends Exchange> exchangeClass;

  @Parameterized.Parameter(1)
  public String exchangeName;

  private Exchange exchange;

  final static Logger logger = LoggerFactory.getLogger(AllIntegration.class);

  @Before
  public void createExchange() {

    exchange = ExchangeFactory.INSTANCE.createExchange(exchangeClass.getName());
    assertThat(exchange).isNotNull();
  }

  @Test
  public void testGetMetaData() throws IOException {

    exchange.remoteInit();

    ExchangeMetaData exchangeMetaData = exchange.getExchangeMetaData();
    assertThat(exchangeMetaData).isNotNull();

    Map<CurrencyPair, CurrencyPairMetaData> marketMetaDataMap = exchangeMetaData.getCurrencyPairs();
    assertThat(marketMetaDataMap).isNotEmpty();

    Map<Currency, CurrencyMetaData> currencyMetaDataMap = exchangeMetaData.getCurrencies();
    assertThat(currencyMetaDataMap).isNotNull();
  }

  // Predicate specifies which exceptions indicate to retry the request
  public static final IPredicate<Exception> RETRYABLE_REQUEST = new IPredicate<Exception>() {
    @Override
    public boolean test(Exception e) {
      return (e.getMessage() != null && e.getMessage().contains("{code=200, message=Too many requests}"))
          || e instanceof SocketTimeoutException /*
                                                  * || e instanceof HttpStatusIOException
                                                  */;
    }
  };

  // Test some service method for a collection of first arguments, catching for example NotYetImplementedForExchangeException
  private <R, A> Collection<R> testExchangeMethod(final Object service, final Method method, Collection<A> firstArgumentOptions,
      Object... restStaticArguments) throws Throwable {

    Assume.assumeNotNull(service);

    String methodName = method.getName();
    Collection<R> results = new ArrayList<>(firstArgumentOptions.size());
    final ArrayList<Object> arguments = new ArrayList<>(restStaticArguments.length + 1);
    arguments.add(null);
    arguments.addAll(Arrays.asList(restStaticArguments));

    // If one argument throws NotAvailableFromExchange, all must.
    boolean notAvailableFromExchangeThrown = false;
    boolean notAvailableFromExchangeNotThrown = false;

    for (final A firstArgument : firstArgumentOptions) {

      Callable<R> callMethod = new Callable<R>() {

        @Override
        public R call() throws Exception {

          try {
            arguments.set(0, firstArgument);
            return (R) method.invoke(service, arguments.toArray());
          } catch (InvocationTargetException invocationTargetException) {
            throw ((Exception) invocationTargetException.getCause());
          }
        }
      };

      try {

        R result = Retries.callWithRetries(8, 1, callMethod, RETRYABLE_REQUEST);

        assertThat(notAvailableFromExchangeThrown).isFalse();
        notAvailableFromExchangeNotThrown = true;

        String logmsg = methodName + "(" + firstArgument + ") -> " + result;
        if (logmsg.length() > 75) {
          logmsg = logmsg.substring(0, 75);
        }
        logger.debug(logmsg);

        assertThat(result).isNotNull();
        results.add(result);

      } catch (NotAvailableFromExchangeException e) {

        assertThat(notAvailableFromExchangeNotThrown).isFalse();
        notAvailableFromExchangeThrown = true;

      } catch (NotYetImplementedForExchangeException e) {

        logger.warn(methodName + " unimplemented");

      }
    }

    Assume.assumeFalse(methodName + " not available from exchange", notAvailableFromExchangeThrown);

    return results;
  }

  // Returns collection of currency pairs
  private Collection<CurrencyPair> getCurrencyPairs() throws IOException {

    exchange.remoteInit();

    Assume.assumeNotNull(exchange.getExchangeMetaData());
    Assume.assumeNotNull(exchange.getExchangeMetaData().getCurrencyPairs());

    // uncomment to test every single currencypair
    //return exchange.getMetaData().getMarketMetaDataMap().keySet();

    return Collections.singletonList(exchange.getExchangeMetaData().getCurrencyPairs().keySet().iterator().next());
  }

  @Test
  public void testGetTicker() throws Throwable {

    Method method = MarketDataService.class.getMethod("getTicker", CurrencyPair.class, Object[].class);
    testExchangeMethod(exchange.getMarketDataService(), method, getCurrencyPairs(), (Object) new Object[]{});
  }

  @Test
  public void testGetOrderBook() throws Throwable {

    Method method = MarketDataService.class.getMethod("getOrderBook", CurrencyPair.class, Object[].class);
    testExchangeMethod(exchange.getMarketDataService(), method, getCurrencyPairs(), (Object) new Object[]{});
  }

  @Test
  public void testGetTrades() throws Throwable {

    Method method = MarketDataService.class.getMethod("getTrades", CurrencyPair.class, Object[].class);
    testExchangeMethod(exchange.getMarketDataService(), method, getCurrencyPairs(), (Object) new Object[]{});
  }
}
