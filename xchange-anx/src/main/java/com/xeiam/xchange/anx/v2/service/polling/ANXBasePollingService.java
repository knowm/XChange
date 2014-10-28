package com.xeiam.xchange.anx.v2.service.polling;

import java.util.Arrays;
import java.util.List;

import si.mazi.rescu.SynchronizedValueFactory;

import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.service.BaseExchangeService;
import com.xeiam.xchange.service.polling.BasePollingService;

/**
 * <p>
 * Implementation of the market data service for ANX V2
 * </p>
 * <ul>
 * <li>Provides access to various market data values</li>
 * </ul>
 */
public class ANXBasePollingService extends BaseExchangeService implements BasePollingService {

  protected static final String PREFIX = "anx";
  protected static final String KEY_ORDER_SIZE_MIN_DEFAULT = PREFIX + SUF_ORDER_SIZE_MIN_DEFAULT;
  protected static final String KEY_ORDER_SIZE_SCALE_DEFAULT = PREFIX + SUF_ORDER_SIZE_SCALE_DEFAULT;
  protected static final String PREKEY_ORDER_SIZE_MIN = PREFIX + IN_ORDER_SIZE_MIN;
  protected static final String PREKEY_ORDER_SIZE_MAX = PREFIX + ".order.size.max.";
  protected static final String KEY_ORDER_SIZE_MAX_DEFAULT = PREKEY_ORDER_SIZE_MAX + SUF_DEFAULT;
  protected static final String KEY_ORDER_PRICE_SCALE_DEFAULT = PREFIX + SUF_ORDER_PRICE_SCALE_DEFAULT;
  protected static final String KEY_ORDER_FEE_POLICY_MAKER = PREFIX + ".order.feePolicy.maker";
  protected static final String KEY_ORDER_FEE_DISCOUNT = PREFIX + ".order.fee.makerDiscount";

  static final List<CurrencyPair> CURRENCY_PAIRS = Arrays.asList(

  new CurrencyPair("BTC", "USD"),

  new CurrencyPair("BTC", "HKD"),

  new CurrencyPair("BTC", "EUR"),

  new CurrencyPair("BTC", "CAD"),

  new CurrencyPair("BTC", "AUD"),

  new CurrencyPair("BTC", "SGD"),

  new CurrencyPair("BTC", "JPY"),

  new CurrencyPair("BTC", "CHF"),

  new CurrencyPair("BTC", "GBP"),

  new CurrencyPair("BTC", "NZD"),

  new CurrencyPair("LTC", "BTC"),

  new CurrencyPair("LTC", "USD"),

  new CurrencyPair("LTC", "HKD"),

  new CurrencyPair("LTC", "EUR"),

  new CurrencyPair("LTC", "CAD"),

  new CurrencyPair("LTC", "AUD"),

  new CurrencyPair("LTC", "SGD"),

  new CurrencyPair("LTC", "JPY"),

  new CurrencyPair("LTC", "CHF"),

  new CurrencyPair("LTC", "GBP"),

  new CurrencyPair("LTC", "NZD"),

  new CurrencyPair("PPC", "BTC"),

  new CurrencyPair("PPC", "LTC"),

  new CurrencyPair("PPC", "USD"),

  new CurrencyPair("PPC", "HKD"),

  new CurrencyPair("PPC", "EUR"),

  new CurrencyPair("PPC", "CAD"),

  new CurrencyPair("PPC", "AUD"),

  new CurrencyPair("PPC", "SGD"),

  new CurrencyPair("PPC", "JPY"),

  new CurrencyPair("PPC", "CHF"),

  new CurrencyPair("PPC", "GBP"),

  new CurrencyPair("PPC", "NZD"),

  new CurrencyPair("NMC", "BTC"),

  new CurrencyPair("NMC", "LTC"),

  new CurrencyPair("NMC", "USD"),

  new CurrencyPair("NMC", "HKD"),

  new CurrencyPair("NMC", "EUR"),

  new CurrencyPair("NMC", "CAD"),

  new CurrencyPair("NMC", "AUD"),

  new CurrencyPair("NMC", "SGD"),

  new CurrencyPair("NMC", "JPY"),

  new CurrencyPair("NMC", "CHF"),

  new CurrencyPair("NMC", "GBP"),

  new CurrencyPair("NMC", "NZD"),

  new CurrencyPair("DOGE", "BTC"),

  new CurrencyPair("DOGE", "USD"),

  new CurrencyPair("DOGE", "HKD"),

  new CurrencyPair("DOGE", "EUR"),

  new CurrencyPair("DOGE", "CAD"),

  new CurrencyPair("DOGE", "AUD"),

  new CurrencyPair("DOGE", "SGD"),

  new CurrencyPair("DOGE", "JPY"),

  new CurrencyPair("DOGE", "CHF"),

  new CurrencyPair("DOGE", "GBP"),

  new CurrencyPair("DOGE", "NZD")

  );

  private final SynchronizedValueFactory<Long> nonceFactory;

  /**
   * Constructor
   *
   * @param exchangeSpecification
   */
  public ANXBasePollingService(ExchangeSpecification exchangeSpecification, SynchronizedValueFactory<Long> nonceFactory) {

    super(exchangeSpecification);
    this.nonceFactory = nonceFactory;
  }

  @Override
  public List<CurrencyPair> getExchangeSymbols() {

    return CURRENCY_PAIRS;
  }

  protected SynchronizedValueFactory<Long> getNonce() {

    return nonceFactory;
  }

}
