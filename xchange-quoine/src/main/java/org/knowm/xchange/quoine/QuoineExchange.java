package org.knowm.xchange.quoine;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.knowm.xchange.BaseExchange;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.quoine.dto.marketdata.QuoineProduct;
import org.knowm.xchange.quoine.service.QuoineAccountService;
import org.knowm.xchange.quoine.service.QuoineMarketDataService;
import org.knowm.xchange.quoine.service.QuoineTradeService;
import org.knowm.xchange.utils.nonce.CurrentTimeNonceFactory;
import si.mazi.rescu.SynchronizedValueFactory;

public class QuoineExchange extends BaseExchange implements Exchange {

  private SynchronizedValueFactory<Long> nonceFactory = new CurrentTimeNonceFactory();
  private Map<CurrencyPair, Integer> products;

  @Override
  protected void initServices() {

    boolean useMargin =
        (Boolean) exchangeSpecification.getExchangeSpecificParametersItem("Use_Margin");

    this.marketDataService = new QuoineMarketDataService(this);
    this.accountService = new QuoineAccountService(this, useMargin);
    this.tradeService = new QuoineTradeService(this, useMargin);
  }

  @Override
  public ExchangeSpecification getDefaultExchangeSpecification() {

    ExchangeSpecification exchangeSpecification =
        new ExchangeSpecification(this.getClass().getCanonicalName());
    exchangeSpecification.setSslUri("https://api.quoine.com");
    exchangeSpecification.setExchangeName("Quoine");
    exchangeSpecification.setExchangeSpecificParametersItem("Use_Margin", false);
    exchangeSpecification.setExchangeSpecificParametersItem("Leverage_Level", "1");
    exchangeSpecification.setHttpReadTimeout(10000);
    return exchangeSpecification;
  }

  @Override
  public SynchronizedValueFactory<Long> getNonceFactory() {

    return nonceFactory;
  }

  @Override
  public void remoteInit() throws IOException, ExchangeException {
    super.remoteInit();

    QuoineProduct[] quoineProducts =
        ((QuoineMarketDataService) marketDataService).getQuoineProducts();
    Map<CurrencyPair, Integer> products = new HashMap<>();
    for (QuoineProduct quoineProduct : quoineProducts) {
      int id = quoineProduct.getId();
      String baseCurrency = quoineProduct.getBaseCurrency();
      String quotedCurrency = quoineProduct.getQuotedCurrency();
      CurrencyPair currencyPair = new CurrencyPair(baseCurrency, quotedCurrency);
      products.put(currencyPair, id);
    }
    this.products = products;
  }

  public Integer getProductId(CurrencyPair currencyPair) {
    return products.get(currencyPair);
  }
}
