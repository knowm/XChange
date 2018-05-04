package org.knowm.xchange.idex;

import static java.math.BigDecimal.ZERO;
import static java.util.Arrays.asList;
import static org.knowm.xchange.idex.IdexMarketDataService.Companion.allCurrenciesStatic;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Iterator;
import java.util.LinkedHashMap;
import org.knowm.xchange.BaseExchange;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.meta.CurrencyMetaData;
import org.knowm.xchange.dto.meta.CurrencyPairMetaData;
import org.knowm.xchange.dto.meta.ExchangeMetaData;
import org.knowm.xchange.dto.meta.RateLimit;
import org.knowm.xchange.idex.IdexExchange.Companion.IdexCurrencyMeta;
import org.knowm.xchange.idex.IdexExchange.Companion.IdexExchangeSpecification;
import org.knowm.xchange.idex.dto.NextNonceReq;
import org.knowm.xchange.idex.dto.ReturnCurrenciesResponse;
import org.knowm.xchange.idex.dto.ReturnNextNonceResponse;
import org.knowm.xchange.idex.dto.ReturnTickerRequestedWithNull;
import org.knowm.xchange.idex.service.ReturnNextNonceApi;
import si.mazi.rescu.RestProxyFactory;
import si.mazi.rescu.SynchronizedValueFactory;

public class IdexExchange extends BaseExchange {
  private ReturnCurrenciesResponse allCurrenciesStatic;
  CurrencyPairMetaData unavailableCPMeta = new CurrencyPairMetaData(ZERO, ZERO, ZERO, 0);
  private ReturnNextNonceApi returnNextNonceApi =
      RestProxyFactory.createProxy(ReturnNextNonceApi.class, exchangeSpecification.getSslUri());

  public final CurrencyPairMetaData getUnavailableCPMeta() {
    return unavailableCPMeta;
  }

  public final ExchangeMetaData getExchangeMetaData() {
    ReturnCurrenciesResponse allCurrenciesStatic = null;
    try {
      allCurrenciesStatic = allCurrenciesStatic();
    } catch (IOException e) {
      e.printStackTrace();
    }
    LinkedHashMap<CurrencyPair, CurrencyPairMetaData> currencyPairs = new LinkedHashMap<>();
    ReturnTickerRequestedWithNull allTickers = IdexMarketDataService.Companion.allTickers;

    allTickers
        .keySet()
        .forEach(
            s -> currencyPairs.put(IdexExchange.Companion.getCurrencyPair(s), unavailableCPMeta));
    LinkedHashMap<Currency, CurrencyMetaData> linkedHashMap = new LinkedHashMap<>();
    allCurrenciesStatic.forEach(
        (key, value) ->
            linkedHashMap.put(
                Currency.getInstance(key),
                new IdexCurrencyMeta(
                    0, ZERO, value.getAddress(), value.getName(), value.getDecimals())));
    RateLimit[] publicRateLimits = {};
    return new ExchangeMetaData(
        currencyPairs, linkedHashMap, publicRateLimits, publicRateLimits, Boolean.FALSE);
  }

  private IdexAccountService idexAccountService;
  private IdexTradeService idexTradeService;
  private IdexMarketDataService idexMarketDataService;
  private ReturnNextNonceApi nextNonceApi;

  public IdexExchange() {}

  public ReturnNextNonceApi getNextNonceApi() {
    if (null == nextNonceApi) {
      nextNonceApi = returnNextNonceApi;
    }
    return nextNonceApi;
  }

  public IdexAccountService getAccountService() {
    if (null == idexAccountService) idexAccountService = new IdexAccountService(this);
    return idexAccountService;
  }

  public IdexMarketDataService getMarketDataService() {
    if (null == idexMarketDataService) idexMarketDataService = new IdexMarketDataService(this);
    return idexMarketDataService;
  }

  public IdexTradeService getTradeService() {
    if (null == idexTradeService) idexTradeService = new IdexTradeService(this);
    return idexTradeService;
  }

  @Override
  protected void initServices() {}

  @Override
  public SynchronizedValueFactory<Long> getNonceFactory() {
    return (SynchronizedValueFactory)
        () -> {
          Long ret = null;
          try {
            ReturnNextNonceResponse var10000 =
                getNextNonceApi()
                    .nextNonce(new NextNonceReq().address(getExchangeSpecification().getApiKey()));
            ret = var10000.getNonce().longValue();
          } catch (Exception e) {
            e.printStackTrace();
          }
          return ret;
        };
  }

  @Override
  public ExchangeSpecification getDefaultExchangeSpecification() {
    return new IdexExchangeSpecification();
  }

  public enum Companion {
    ;
    public static final String IDEX_API =
        System.getProperty("xchange.idex.api", "https://api.idex.market/");

    public static BigDecimal safeParse(String s) {
      BigDecimal ret = null;
      try {
        ret = new BigDecimal(s);
      } catch (Exception e) {
      }
      return ret;
    }

    public static String getMarket(CurrencyPair currencyPair) {
      return currencyPair.base.getSymbol() + "_" + currencyPair.counter.getSymbol();
    }

    public static CurrencyPair getCurrencyPair(String market) {
      CurrencyPair currencyPair;
      Iterator<String> syms = asList(market.split("_")).iterator();
      currencyPair = new CurrencyPair(syms.next(), syms.next());
      return currencyPair;
    }

    public static class IdexExchangeSpecification extends ExchangeSpecification {
      public IdexExchangeSpecification() {
        super(IdexExchange.class);
      }

      @Override
      public String getExchangeClassName() {
        return getClass().getCanonicalName();
      }

      @Override
      public String getExchangeName() {
        return "idex";
      }

      @Override
      public String getSslUri() {
        return IDEX_API;
      }
    }

    public static final class IdexCurrencyMeta extends CurrencyMetaData {
      private final String address;
      private final String name;
      private final BigInteger decimals;

      public IdexCurrencyMeta(
          int scale, BigDecimal withdrawalFee, String address, String name, BigInteger decimals) {
        super(scale, withdrawalFee);
        this.address = address;
        this.name = name;
        this.decimals = decimals;
      }

      public final String getAddress() {
        return address;
      }

      public final String getName() {
        return name;
      }

      public final BigInteger getDecimals() {
        return decimals;
      }
    }
  }
}
