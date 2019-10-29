package org.knowm.xchange.latoken;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import org.knowm.xchange.BaseExchange;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.meta.CurrencyMetaData;
import org.knowm.xchange.dto.meta.CurrencyPairMetaData;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.latoken.dto.exchangeinfo.LatokenCurrency;
import org.knowm.xchange.latoken.dto.exchangeinfo.LatokenPair;
import org.knowm.xchange.latoken.service.LatokenAccountService;
import org.knowm.xchange.latoken.service.LatokenMarketDataService;
import org.knowm.xchange.latoken.service.LatokenTradeService;
import org.knowm.xchange.utils.AuthUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import si.mazi.rescu.RestProxyFactory;
import si.mazi.rescu.SynchronizedValueFactory;

public class LatokenExchange extends BaseExchange {

  private static final Logger LOG = LoggerFactory.getLogger(LatokenExchange.class);
  private static final int PRECISION = 8;

  private LatokenAuthenticated latoken;

  @Override
  protected void initServices() {
    this.latoken =
        RestProxyFactory.createProxy(
            LatokenAuthenticated.class, getExchangeSpecification().getSslUri());
    this.marketDataService = new LatokenMarketDataService(this);
    this.tradeService = new LatokenTradeService(this);
    this.accountService = new LatokenAccountService(this);
  }

  /** Latoken uses HMAC signature and timing-validation to identify valid requests. */
  @Override
  public SynchronizedValueFactory<Long> getNonceFactory() {

    throw new UnsupportedOperationException(
        "Latoken uses HMAC signature and timing-validation rather than a nonce");
  }

  @Override
  public ExchangeSpecification getDefaultExchangeSpecification() {

    ExchangeSpecification spec = new ExchangeSpecification(this.getClass().getCanonicalName());
    spec.setSslUri("https://api.latoken.com");
    spec.setHost("www.latoken.com");
    spec.setPort(80);
    spec.setExchangeName("Latoken");
    spec.setExchangeDescription("LATOKEN Exchange.");
    AuthUtils.setApiAndSecretKey(spec, "latoken");
    return spec;
  }

  @Override
  public void remoteInit() {

    try {
      // Load the static meta-data and override with the dynamic one
      Map<Currency, CurrencyMetaData> currenciesMetaData = exchangeMetaData.getCurrencies();
      Map<CurrencyPair, CurrencyPairMetaData> pairsMetaData = exchangeMetaData.getCurrencyPairs();

      List<LatokenPair> allPairs = latoken.getAllPairs();
      List<LatokenCurrency> allCurrencies = latoken.getAllCurrencies();

      // Save pairs map on the exchange
      this.exchangeSpecification.setExchangeSpecificParametersItem("pairs", allPairs);
      // Update Currency meta-data
      for (LatokenCurrency latokenCurrency : allCurrencies) {
        Currency currency = LatokenAdapters.adaptCurrency(latokenCurrency);
        addCurrencyMetadata(currenciesMetaData, currency, PRECISION);
      }

      // Update CurrencyPair meta-data
      for (LatokenPair latokenPair : allPairs) {
        CurrencyPair pair = LatokenAdapters.adaptCurrencyPair(latokenPair);
        CurrencyPairMetaData pairMetadata = LatokenAdapters.adaptPairMetaData(latokenPair);
        addCurrencyPairMetadata(pairsMetaData, pair, pairMetadata);
      }
    } catch (Exception e) {
      throw new ExchangeException("Failed to initialize: " + e.getMessage(), e);
    }
  }

  /**
   * Updates the meta-data entry of a given {@link Currency}. <br>
   * Used for overriding the static meta-data with dynamic one received from the exchange.
   *
   * @param currencies
   * @param currency
   * @param precision
   */
  private void addCurrencyMetadata(
      Map<Currency, CurrencyMetaData> currencies, Currency currency, int precision) {

    CurrencyMetaData baseMetaData = currencies.get(currency);

    // Preserve withdrawal-fee if exists
    BigDecimal withdrawalFee = baseMetaData == null ? null : baseMetaData.getWithdrawalFee();

    // Override static meta-data
    currencies.put(currency, new CurrencyMetaData(precision, withdrawalFee));
  }

  /**
   * Updates the meta-data entry of a given {@link CurrencyPair}. <br>
   * Used for overriding the static meta-data with dynamic one received from the exchange.
   *
   * @param pairs
   * @param pair
   * @param precision
   */
  private void addCurrencyPairMetadata(
      Map<CurrencyPair, CurrencyPairMetaData> pairs,
      CurrencyPair pair,
      CurrencyPairMetaData pairMetadata) {

    CurrencyPairMetaData baseMetaData = pairs.get(pair);

    // Preserve MaxAmount if exists
    BigDecimal maxAmount =
        baseMetaData == null ? pairMetadata.getMaximumAmount() : baseMetaData.getMaximumAmount();

    // Override static meta-data
    pairs.put(
        pair,
        new CurrencyPairMetaData.Builder()
            .tradingFee(pairMetadata.getTradingFee())
            .minimumAmount(pairMetadata.getMinimumAmount())
            .maximumAmount(maxAmount)
            .priceScale(pairMetadata.getPriceScale())
            .build());
  }
}
