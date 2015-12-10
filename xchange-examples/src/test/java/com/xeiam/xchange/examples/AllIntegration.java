package com.xeiam.xchange.examples;

import static org.fest.assertions.api.Assertions.assertThat;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.net.SocketTimeoutException;
import java.util.*;
import java.util.concurrent.Callable;

import org.hamcrest.CoreMatchers;
import org.junit.Assume;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.ExchangeFactory;
import com.xeiam.xchange.currency.Currency;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.meta.CurrencyMetaData;
import com.xeiam.xchange.dto.meta.ExchangeMetaData;
import com.xeiam.xchange.dto.meta.MarketMetaData;
import com.xeiam.xchange.exceptions.NotAvailableFromExchangeException;
import com.xeiam.xchange.exceptions.NotYetImplementedForExchangeException;
import com.xeiam.xchange.service.polling.marketdata.PollingMarketDataService;
import com.xeiam.xchange.utils.retries.IPredicate;
import com.xeiam.xchange.utils.retries.Retries;

import junit.framework.TestSuite;
import si.mazi.rescu.HttpStatusIOException;

@RunWith(Parameterized.class)
public class AllIntegration {

  @Parameterized.Parameters(name="{index}:{1}")
  public static Iterable<Object[]> data() {

    List<Object[]> exchangeClasses = new ArrayList<Object[]>();

    // Find every Exchange
    Reflections reflections = new Reflections("com.xeiam.xchange");
    for (Class<? extends Exchange> exchangeClass : reflections.getSubTypesOf(Exchange.class)) {
      if (Modifier.isAbstract(exchangeClass.getModifiers()))
        continue;

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

    ExchangeMetaData exchangeMetaData = exchange.getMetaData();
    assertThat(exchangeMetaData).isNotNull();

    Map<CurrencyPair,MarketMetaData> marketMetaDataMap = exchangeMetaData.getMarketMetaDataMap();
    assertThat(marketMetaDataMap).isNotEmpty();

    Map<Currency,CurrencyMetaData> currencyMetaDataMap = exchangeMetaData.getCurrencyMetaDataMap();
    assertThat(currencyMetaDataMap).isNotNull();
  }

  // Predicate specifies which exceptions indicate to retry the request
  public static final IPredicate<Exception> RETRYABLE_REQUEST = new IPredicate<Exception>() {
    @Override
    public boolean test(Exception e) {
      return (e.getMessage() != null && e.getMessage().contains("{code=200, message=Too many requests}")) ||
          e instanceof SocketTimeoutException /*||
          e instanceof HttpStatusIOException*/;
    }
  };

  // Test some service method for a collection of first arguments, catching for example NotYetImplementedForExchangeException
  private <R,A> Collection<R> testExchangeMethod(final Object service, final Method method, Collection<A> firstArgumentOptions, Object...
      restStaticArguments)
      throws Throwable
  {

    Assume.assumeNotNull(service);

    String methodName = method.getName();
    Collection<R> results = new ArrayList<R>(firstArgumentOptions.size());
    final ArrayList<Object> arguments = new ArrayList<Object>(restStaticArguments.length + 1);
    arguments.add(null);
    arguments.addAll(Arrays.asList(restStaticArguments));

    // If one argument throws NotAvailableFromExchange, all must.
    boolean notAvailableFromExchangeThrown = false;
    boolean notAvailableFromExchangeNotThrown = false;

    for (final A firstArgument : firstArgumentOptions){

      Callable<R> callMethod = new Callable<R>() {

        @Override
        public R call() throws Exception {

          try {
            arguments.set(0, firstArgument);
            return (R) method.invoke(service, arguments.toArray());
          } catch (InvocationTargetException invocationTargetException) {
            throw((Exception)invocationTargetException.getCause());
          }
        }
      };

      try {

        R result = Retries.callWithRetries(8, 1, callMethod, RETRYABLE_REQUEST);

        assertThat(notAvailableFromExchangeThrown).isFalse();
        notAvailableFromExchangeNotThrown = true;

        logger.debug((methodName + "(" + firstArgument + ") -> " + result).substring(0,75));

        assertThat(result).isNotNull();
        results.add(result);

      } catch(NotAvailableFromExchangeException e) {

        assertThat(notAvailableFromExchangeNotThrown).isFalse();
        notAvailableFromExchangeThrown = true;

      } catch(NotYetImplementedForExchangeException e) {

        logger.warn(methodName + " unimplemented");

      }
    }

    Assume.assumeFalse(methodName + " not available from exchange", notAvailableFromExchangeThrown);

    return results;
  }

  // Returns collection of currency pairs
  private Collection<CurrencyPair> getCurrencyPairs() throws IOException {

    exchange.remoteInit();

    Assume.assumeNotNull(exchange.getMetaData());
    Assume.assumeNotNull(exchange.getMetaData().getMarketMetaDataMap());

    // uncomment to test every single currencypair
    //return exchange.getMetaData().getMarketMetaDataMap().keySet();

    return Collections.singletonList(exchange.getMetaData().getMarketMetaDataMap().keySet().iterator().next());
  }

  @Test
  public void testGetTicker() throws Throwable {

    Method method = PollingMarketDataService.class.getMethod("getTicker", CurrencyPair.class, Object[].class);
    testExchangeMethod(exchange.getPollingMarketDataService(), method, getCurrencyPairs(), (Object)new Object[]{});
  }

  @Test
  public void testGetOrderBook() throws Throwable {

    Method method = PollingMarketDataService.class.getMethod("getOrderBook", CurrencyPair.class, Object[].class);
    testExchangeMethod(exchange.getPollingMarketDataService(), method, getCurrencyPairs(), (Object)new Object[]{});
  }

  @Test
  public void testGetTrades() throws Throwable {

    Method method = PollingMarketDataService.class.getMethod("getTrades", CurrencyPair.class, Object[].class);
    testExchangeMethod(exchange.getPollingMarketDataService(), method, getCurrencyPairs(), (Object)new Object[]{});
  }
}
