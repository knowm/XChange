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

public class CoindirectExchange extends BaseExchange {

  @Override
  protected void initServices() {
    this.marketDataService = new CoindirectMarketDataService(this);
    this.accountService = new CoindirectAccountService(this);
    this.tradeService = new CoindirectTradeService(this);
  }

  @Override
  public ExchangeSpecification getDefaultExchangeSpecification() {
    ExchangeSpecification spec = new ExchangeSpecification(this.getClass());
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

      for (CoindirectMarket market : coindirectMarketList) {
        CurrencyPair currencyPair = CoindirectAdapters.toCurrencyPair(market.symbol);
        CurrencyPairMetaData staticMeta = currencyPairs.get(currencyPair);
        CurrencyPairMetaData adaptedMeta =
            new CurrencyPairMetaData(
                staticMeta.getTradingFee(),
                market.minimumQuantity,
                market.maximumQuantity,
                staticMeta.getPriceScale(),
                staticMeta.getFeeTiers());
        currencyPairs.put(currencyPair, adaptedMeta);
      }

    } catch (IOException exception) {

    }
  }
}
