package org.knowm.xchange.bitflyer;

import java.io.IOException;
import java.util.List;
import org.knowm.xchange.BaseExchange;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.bitflyer.dto.account.BitflyerMarket;
import org.knowm.xchange.bitflyer.service.BitflyerAccountService;
import org.knowm.xchange.bitflyer.service.BitflyerMarketDataService;
import org.knowm.xchange.bitflyer.service.BitflyerMarketDataServiceRaw;
import org.knowm.xchange.bitflyer.service.BitflyerTradeService;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.utils.nonce.AtomicLongIncrementalTime2014NonceFactory;
import si.mazi.rescu.SynchronizedValueFactory;

public class BitflyerExchange extends BaseExchange implements Exchange {

  private SynchronizedValueFactory<Long> nonceFactory =
      new AtomicLongIncrementalTime2014NonceFactory();

  @Override
  protected void initServices() {
    this.marketDataService = new BitflyerMarketDataService(this);
    this.accountService = new BitflyerAccountService(this);
    this.tradeService = new BitflyerTradeService(this);
  }

  @Override
  public ExchangeSpecification getDefaultExchangeSpecification() {

    ExchangeSpecification exchangeSpecification =
        new ExchangeSpecification(this.getClass().getCanonicalName());
    exchangeSpecification.setSslUri("https://api.bitflyer.jp/");
    exchangeSpecification.setHost("api.bitflyer.jp");
    exchangeSpecification.setPort(80);
    exchangeSpecification.setExchangeName("BitFlyer");

    return exchangeSpecification;
  }

  @Override
  public SynchronizedValueFactory<Long> getNonceFactory() {

    return nonceFactory;
  }

  @Override
  public void remoteInit() throws IOException, ExchangeException {

    BitflyerMarketDataServiceRaw dataService =
        (BitflyerMarketDataServiceRaw) this.marketDataService;
    List<BitflyerMarket> markets = dataService.getMarkets();
    exchangeMetaData = BitflyerAdapters.adaptMetaData(markets);
  }
}
