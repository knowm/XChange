package org.knowm.xchange.coinex;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.knowm.xchange.BaseExchange;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.coinex.dto.marketdata.CoinexCurrencyPairInfo;
import org.knowm.xchange.coinex.service.CoinexAccountService;
import org.knowm.xchange.coinex.service.CoinexMarketDataService;
import org.knowm.xchange.coinex.service.CoinexMarketDataServiceRaw;
import org.knowm.xchange.coinex.service.CoinexTradeService;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.meta.ExchangeMetaData;
import org.knowm.xchange.dto.meta.InstrumentMetaData;
import org.knowm.xchange.instrument.Instrument;

public class CoinexExchange extends BaseExchange {

  @Override
  protected void initServices() {
    accountService = new CoinexAccountService(this);
    marketDataService = new CoinexMarketDataService(this);
    tradeService = new CoinexTradeService(this);
  }

  @Override
  public ExchangeSpecification getDefaultExchangeSpecification() {
    ExchangeSpecification specification = new ExchangeSpecification(this.getClass());
    specification.setSslUri("https://api.coinex.com");
    specification.setHost("www.coinex.com");
    specification.setExchangeName("Coinex");
    return specification;
  }

  @Override
  public void remoteInit() throws IOException {
    CoinexMarketDataServiceRaw coinexMarketDataServiceRaw =
        (CoinexMarketDataServiceRaw) marketDataService;

    // initialize symbol mappings
    List<CoinexCurrencyPairInfo> currencyPairInfos =
        coinexMarketDataServiceRaw.getCoinexCurrencyPairInfos(null);
    currencyPairInfos.forEach(
        currencyPairInfo -> {
          CoinexAdapters.putSymbolMapping(
              currencyPairInfo.getSymbol(),
              new CurrencyPair(
                  currencyPairInfo.getBaseCurrency(), currencyPairInfo.getQuoteCurrency()));
        });

    // initialize instrument metadata
    Map<Instrument, InstrumentMetaData> instruments =
        currencyPairInfos.stream()
            .collect(
                Collectors.toMap(
                    CoinexCurrencyPairInfo::getCurrencyPair, CoinexAdapters::toInstrumentMetaData));

    exchangeMetaData = new ExchangeMetaData(instruments, null, null, null, null);
  }
}
