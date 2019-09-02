package org.knowm.xchange.lgo;

import org.knowm.xchange.*;
import org.knowm.xchange.lgo.dto.LgoException;
import org.knowm.xchange.lgo.dto.currency.LgoCurrencies;
import org.knowm.xchange.lgo.dto.product.LgoProducts;
import org.knowm.xchange.lgo.service.*;
import org.knowm.xchange.service.account.AccountService;
import org.knowm.xchange.utils.nonce.CurrentTimeNonceFactory;
import si.mazi.rescu.SynchronizedValueFactory;

import java.io.IOException;

public class LgoExchange extends BaseExchange {

  private SynchronizedValueFactory<Long> nonceFactory = new CurrentTimeNonceFactory();

  private LgoSignatureService signatureService;
  private LgoProducts products;
  private LgoCurrencies currencies;

  @Override
  protected void initServices() {
    signatureService = LgoSignatureService.createInstance(getExchangeSpecification());
    this.marketDataService = new LgoMarketDataService(this);
    this.tradeService = new LgoTradeService(this);
    this.accountService = new AccountService() {};
  }

  @Override
  public void remoteInit() throws IOException {
    try {
      products = getMarketDataService().getProducts();
      currencies = getMarketDataService().getCurrencies();
    } catch (LgoException e) {
      throw LgoErrorAdapter.adapt(e);
    }
    LgoAdapters.adaptMetadata(getExchangeMetaData(), products, currencies);
  }

  @Override
  public ExchangeSpecification getDefaultExchangeSpecification() {
    return LgoEnv.prod();
  }

  @Override
  public LgoMarketDataService getMarketDataService() {
    return (LgoMarketDataService) marketDataService;
  }

  @Override
  public LgoTradeService getTradeService() {
    return (LgoTradeService) super.getTradeService();
  }

  @Override
  public SynchronizedValueFactory<Long> getNonceFactory() {
    return nonceFactory;
  }

  public LgoProducts getProducts() {
    return products;
  }

  public LgoCurrencies getCurrencies() {
    return currencies;
  }

  public LgoSignatureService getSignatureService() {
    return signatureService;
  }
}
