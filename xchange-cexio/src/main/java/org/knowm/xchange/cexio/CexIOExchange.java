package org.knowm.xchange.cexio;

import java.io.IOException;
import java.util.Map;
import org.knowm.xchange.BaseExchange;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.cexio.dto.marketdata.CexIOCurrencyLimits;
import org.knowm.xchange.cexio.service.CexIOAccountService;
import org.knowm.xchange.cexio.service.CexIOMarketDataService;
import org.knowm.xchange.cexio.service.CexIOTradeService;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.meta.InstrumentMetaData;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.instrument.Instrument;
import org.knowm.xchange.utils.nonce.AtomicLongIncrementalTime2014NonceFactory;
import si.mazi.rescu.SynchronizedValueFactory;

public class CexIOExchange extends BaseExchange implements Exchange {

  private SynchronizedValueFactory<Long> nonceFactory =
      new AtomicLongIncrementalTime2014NonceFactory();

  @Override
  protected void initServices() {
    this.marketDataService = new CexIOMarketDataService(this);
    this.accountService = new CexIOAccountService(this);
    this.tradeService = new CexIOTradeService(this);
  }

  @Override
  public ExchangeSpecification getDefaultExchangeSpecification() {

    ExchangeSpecification exchangeSpecification = new ExchangeSpecification(this.getClass());
    exchangeSpecification.setSslUri("https://cex.io");
    exchangeSpecification.setHost("cex.io");
    exchangeSpecification.setPort(80);
    exchangeSpecification.setExchangeName("Cex IO");
    exchangeSpecification.setExchangeDescription(
        "Cex.IO is a virtual commodities exchange registered in United Kingdom.");

    return exchangeSpecification;
  }

  @Override
  public void remoteInit() throws IOException, ExchangeException {
    logger.info("Attempting remoteInit for {}", getExchangeSpecification().getExchangeName());
    final CexIOCurrencyLimits currencyLimits =
        ((CexIOMarketDataService) this.marketDataService).getCurrencyLimits();
    // Working with the live map so the changes reflect
    final Map<Instrument, InstrumentMetaData> currencyPairs =
        getExchangeMetaData().getInstruments();

    for (CexIOCurrencyLimits.Pair pair : currencyLimits.getData().getPairs()) {
      CurrencyPair currencyPair = new CurrencyPair(pair.getSymbol1(), pair.getSymbol2());
      InstrumentMetaData metaData =
          new InstrumentMetaData.Builder().minimumAmount(pair.getMinLotSize()).maximumAmount(pair.getMaxLotSize()).build();
      currencyPairs.merge(
          currencyPair,
          metaData,
          (oldMetaData, newMetaData) ->
                  new InstrumentMetaData.Builder()
                          .tradingFee(oldMetaData.getTradingFee())
                          .minimumAmount(newMetaData.getMinimumAmount())
                          .maximumAmount(newMetaData.getMaximumAmount() != null
                                  ? newMetaData.getMaximumAmount()
                                  : oldMetaData.getMaximumAmount())
                          .priceScale(oldMetaData.getPriceScale())
                          .feeTiers(newMetaData.getFeeTiers() != null
                                  ? newMetaData.getFeeTiers()
                                  : oldMetaData.getFeeTiers())
                          .build());

    }
    logger.info("remoteInit successful for {}", getExchangeSpecification().getExchangeName());
  }

  @Override
  public SynchronizedValueFactory<Long> getNonceFactory() {

    return nonceFactory;
  }
}
