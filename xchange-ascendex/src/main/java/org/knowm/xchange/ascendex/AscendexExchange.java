package org.knowm.xchange.ascendex;

import java.io.IOException;
import org.knowm.xchange.BaseExchange;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.ascendex.service.AscendexAccountService;
import org.knowm.xchange.ascendex.service.AscendexMarketDataService;
import org.knowm.xchange.ascendex.service.AscendexMarketDataServiceRaw;
import org.knowm.xchange.ascendex.service.AscendexTradeService;
import org.knowm.xchange.exceptions.ExchangeException;

/** @author makarid */
public class AscendexExchange extends BaseExchange implements Exchange {

  @Override
  protected void initServices() {
    this.marketDataService = new AscendexMarketDataService(this);
    this.accountService = new AscendexAccountService(this);
    this.tradeService = new AscendexTradeService(this);
  }

  @Override
  public ExchangeSpecification getDefaultExchangeSpecification() {
    ExchangeSpecification exchangeSpecification = new ExchangeSpecification(this.getClass());
    exchangeSpecification.setSslUri("https://ascendex.com/");
    exchangeSpecification.setExchangeName("Ascendex");
    exchangeSpecification.setExchangeDescription(
        "Ascendex is a Bitcoin exchange with spot and future markets.");
    return exchangeSpecification;
  }

  @Override
  public void remoteInit() throws IOException, ExchangeException {
    AscendexMarketDataServiceRaw raw = ((AscendexMarketDataServiceRaw) getMarketDataService());

    exchangeMetaData =
        AscendexAdapters.adaptExchangeMetaData(raw.getAllAssets(), raw.getAllProducts());
  }
}
