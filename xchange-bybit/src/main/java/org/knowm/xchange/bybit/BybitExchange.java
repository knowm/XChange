package org.knowm.xchange.bybit;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.knowm.xchange.BaseExchange;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.bybit.dto.marketdata.BybitInstrumentInfo;
import org.knowm.xchange.bybit.service.BybitAccountService;
import org.knowm.xchange.bybit.service.BybitMarketDataService;
import org.knowm.xchange.bybit.service.BybitMarketDataServiceRaw;
import org.knowm.xchange.bybit.service.BybitTradeService;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.meta.InstrumentMetaData;
import org.knowm.xchange.instrument.Instrument;

@Slf4j
public class BybitExchange extends BaseExchange {

  @Override
  protected void initServices() {
    marketDataService = new BybitMarketDataService(this);
    tradeService = new BybitTradeService(this);
    accountService = new BybitAccountService(this);
  }

  @Override
  public ExchangeSpecification getDefaultExchangeSpecification() {
    ExchangeSpecification exchangeSpecification = new ExchangeSpecification(this.getClass());
    exchangeSpecification.setSslUri("https://api.bybit.com");
    exchangeSpecification.setHost("bybit.com");
    exchangeSpecification.setPort(80);
    exchangeSpecification.setExchangeName("Bybit");
    exchangeSpecification.setExchangeDescription("BYBIT");
    return exchangeSpecification;
  }

  @Override
  public void remoteInit() throws IOException {
    // initialize currency pairs
    List<BybitInstrumentInfo> instrumentsInfo =
        ((BybitMarketDataServiceRaw) marketDataService).getInstrumentsInfo().getList();

    Map<Instrument, InstrumentMetaData> instruments = new HashMap<>();
    Map<String, CurrencyPair> currencyPairBySymbol = new HashMap<>();
    instrumentsInfo.forEach(
        bybitInstrumentInfo -> {
      instruments.put(
          bybitInstrumentInfo.getCurrencyPair(),
          BybitAdapters.toInstrumentMetaData(bybitInstrumentInfo));

      currencyPairBySymbol
                .put(bybitInstrumentInfo.getSymbol(),
          bybitInstrumentInfo.getCurrencyPair());
    });

    exchangeMetaData = new BybitExchangeMetadata(instruments, null, null, null, null, currencyPairBySymbol);
  }



  public CurrencyPair toCurrencyPair(String symbol) {
    // do remote init again if infos expired
    BybitExchangeMetadata bybitExchangeMetadata = (BybitExchangeMetadata) getExchangeMetaData();

    if (!bybitExchangeMetadata.getCurrencyPairBySymbol().containsKey(symbol)) {
      log.debug("Can't parse symbol {}", symbol);
    }

    return bybitExchangeMetadata.getCurrencyPairBySymbol().get(symbol);
  }
}
