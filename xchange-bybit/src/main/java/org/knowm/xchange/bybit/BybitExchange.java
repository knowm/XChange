package org.knowm.xchange.bybit;

import java.io.IOException;
import org.knowm.xchange.BaseExchange;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.bybit.dto.BybitCategory;
import org.knowm.xchange.bybit.dto.account.walletbalance.BybitAccountType;
import org.knowm.xchange.bybit.dto.marketdata.instruments.BybitInstrumentInfo;
import org.knowm.xchange.bybit.dto.marketdata.instruments.BybitInstrumentsInfo;
import org.knowm.xchange.bybit.dto.marketdata.instruments.linear.BybitLinearInverseInstrumentInfo;
import org.knowm.xchange.bybit.mappers.MarketDataMapper;
import org.knowm.xchange.bybit.service.BybitAccountService;
import org.knowm.xchange.bybit.service.BybitMarketDataService;
import org.knowm.xchange.bybit.service.BybitMarketDataServiceRaw;
import org.knowm.xchange.bybit.service.BybitTradeService;
import org.knowm.xchange.exceptions.ExchangeException;

public class BybitExchange extends BaseExchange {

  @Override
  protected void initServices() {
    marketDataService = new BybitMarketDataService(this);
    tradeService = new BybitTradeService(this);
    accountService =
        new BybitAccountService(
            this, ((BybitExchangeSpecification) getExchangeSpecification()).getAccountType());
  }

  @Override
  public ExchangeSpecification getDefaultExchangeSpecification() {
    BybitExchangeSpecification exchangeSpecification =
        new BybitExchangeSpecification(this.getClass());
    exchangeSpecification.setSslUri("https://api.bybit.com");
    exchangeSpecification.setHost("bybit.com");
    exchangeSpecification.setPort(80);
    exchangeSpecification.setExchangeName("Bybit");
    exchangeSpecification.setExchangeDescription("BYBIT");
    exchangeSpecification.setAccountType(BybitAccountType.UNIFIED);
    return exchangeSpecification;
  }

  @Override
  public void remoteInit() throws IOException, ExchangeException {
    // initialize currency pairs
    BybitInstrumentsInfo<BybitInstrumentInfo> instrumentInfos =
        ((BybitMarketDataServiceRaw) marketDataService)
            .getInstrumentsInfo(BybitCategory.LINEAR)
            .getResult();
    instrumentInfos
        .getList()
        .forEach(
            instrumentInfo ->
                exchangeMetaData
                    .getInstruments()
                    .put(
                        MarketDataMapper.symbolToCurrencyPair(instrumentInfo),
                        MarketDataMapper.symbolToCurrencyPairMetaData(
                            (BybitLinearInverseInstrumentInfo) instrumentInfo)));
  }
}
