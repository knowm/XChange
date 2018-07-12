package org.knowm.xchange.coindirect;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import org.knowm.xchange.BaseExchange;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.coindirect.dto.marketdata.CoindirectMarket;
import org.knowm.xchange.coindirect.service.CoindirectAccountService;
import org.knowm.xchange.coindirect.service.CoindirectMarketDataService;
import org.knowm.xchange.coindirect.service.CoindirectTradeService;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.meta.CurrencyPairMetaData;
import org.knowm.xchange.utils.AuthUtils;
import org.knowm.xchange.utils.nonce.AtomicLongCurrentTimeIncrementalNonceFactory;
import si.mazi.rescu.SynchronizedValueFactory;

public class CoindirectExchange extends BaseExchange {
  private SynchronizedValueFactory<Long> nonceFactory =
      new AtomicLongCurrentTimeIncrementalNonceFactory();

  @Override
  protected void initServices() {
    this.marketDataService = new CoindirectMarketDataService(this);
    this.accountService = new CoindirectAccountService(this);
    this.tradeService = new CoindirectTradeService(this);
  }

  @Override
  public SynchronizedValueFactory<Long> getNonceFactory() {
    return nonceFactory;
  }

  @Override
  public ExchangeSpecification getDefaultExchangeSpecification() {
    ExchangeSpecification spec = new ExchangeSpecification(this.getClass().getCanonicalName());
    spec.setSslUri("https://api.coindirect.com");
    spec.setHost("www.coindirect.com");
    spec.setPort(80);
    spec.setExchangeName("Coindirect");
    spec.setExchangeDescription("Coindirect Exchange.");
    AuthUtils.setApiAndSecretKey(spec, "coindirect");
    return spec;
  }

  @Override
  public void remoteInit() {
    Map<CurrencyPair, CurrencyPairMetaData> currencyPairs = exchangeMetaData.getCurrencyPairs();

    CoindirectMarketDataService coindirectMarketDataService =
        (CoindirectMarketDataService) marketDataService;

    try {
      List<CoindirectMarket> coindirectMarketList =
          coindirectMarketDataService.getCoindirectMarkets(1000);
      CurrencyPairMetaData currencyPairMetaData;

      for (CoindirectMarket market : coindirectMarketList) {
        currencyPairMetaData =
            new CurrencyPairMetaData(null, market.minimumQuantity, market.maximumQuantity, null);
        currencyPairs.put(CoindirectAdapters.toCurrencyPair(market.symbol), currencyPairMetaData);
      }

    } catch (IOException exception) {

    }
  }
}
