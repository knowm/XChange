package org.knowm.xchange.bybit;

import java.io.IOException;
import org.knowm.xchange.BaseExchange;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.bybit.dto.BybitCategory;
import org.knowm.xchange.bybit.dto.account.walletbalance.BybitAccountType;
import org.knowm.xchange.bybit.dto.marketdata.instruments.BybitInstrumentInfo;
import org.knowm.xchange.bybit.dto.marketdata.instruments.BybitInstrumentsInfo;
import org.knowm.xchange.bybit.dto.marketdata.instruments.spot.BybitSpotInstrumentInfo;
import org.knowm.xchange.bybit.mappers.MarketDataMapper;
import org.knowm.xchange.bybit.service.BybitAccountService;
import org.knowm.xchange.bybit.service.BybitMarketDataService;
import org.knowm.xchange.bybit.service.BybitMarketDataServiceRaw;
import org.knowm.xchange.bybit.service.BybitTradeService;
import org.knowm.xchange.exceptions.ExchangeException;

public class BybitExchange extends BaseExchange {

  public static final String SPECIFIC_PARAM_ACCOUNT_TYPE = "accountType";

  @Override
  protected void initServices() {
    marketDataService = new BybitMarketDataService(this);
    tradeService = new BybitTradeService(this);
    accountService =
        new BybitAccountService(
            this,
            (BybitAccountType)
                getExchangeSpecification()
                    .getExchangeSpecificParametersItem(SPECIFIC_PARAM_ACCOUNT_TYPE));
  }

  @Override
  public ExchangeSpecification getDefaultExchangeSpecification() {
    ExchangeSpecification exchangeSpecification = new ExchangeSpecification(this.getClass());
    exchangeSpecification.setSslUri("https://api.bybit.com");
    exchangeSpecification.setHost("bybit.com");
    exchangeSpecification.setPort(80);
    exchangeSpecification.setExchangeName("Bybit");
    exchangeSpecification.setExchangeDescription("BYBIT");
    exchangeSpecification.setExchangeSpecificParametersItem(
        SPECIFIC_PARAM_ACCOUNT_TYPE, BybitAccountType.UNIFIED);
    return exchangeSpecification;
  }

  @Override
  public void remoteInit() throws IOException, ExchangeException {
    // initialize currency pairs
    BybitInstrumentsInfo<BybitInstrumentInfo> instrumentInfos =
        ((BybitMarketDataServiceRaw) marketDataService)
            .getInstrumentsInfo(BybitCategory.SPOT)
            .getResult();

    for (BybitInstrumentInfo instrumentInfo : instrumentInfos.getList()) {
      exchangeMetaData
          .getInstruments()
          .put(
              MarketDataMapper.symbolToCurrencyPair(instrumentInfo),
              MarketDataMapper.symbolToCurrencyPairMetaData(
                  (BybitSpotInstrumentInfo) instrumentInfo));
    }
  }
}
