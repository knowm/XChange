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
import org.knowm.xchange.dto.meta.InstrumentMetaData;
import org.knowm.xchange.instrument.Instrument;
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
    Map<Instrument, InstrumentMetaData> currencyPairs = exchangeMetaData.getInstruments();

    CoindirectMarketDataService coindirectMarketDataService =
        (CoindirectMarketDataService) marketDataService;

    try {
      List<CoindirectMarket> coindirectMarketList =
          coindirectMarketDataService.getCoindirectMarkets(1000);

      for (CoindirectMarket market : coindirectMarketList) {
        CurrencyPair currencyPair = CoindirectAdapters.toCurrencyPair(market.symbol);
        InstrumentMetaData staticMeta = currencyPairs.get(currencyPair);
        currencyPairs.put(
            currencyPair,
            new InstrumentMetaData.Builder()
                .tradingFee(staticMeta.getTradingFee())
                .minimumAmount(market.minimumQuantity)
                .maximumAmount(market.maximumQuantity)
                .priceScale(staticMeta.getPriceScale())
                .feeTiers(staticMeta.getFeeTiers())
                .build());
      }

    } catch (IOException exception) {

    }
  }
}
